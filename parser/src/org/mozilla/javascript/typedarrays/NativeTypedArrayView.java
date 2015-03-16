package org.mozilla.javascript.typedarrays;

import java.util.List;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.IdFunctionObject;
import org.mozilla.javascript.NativeArray;
import org.mozilla.javascript.ScriptRuntime;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.Undefined;


/**
 * The Class NativeTypedArrayView.
 */
public abstract class NativeTypedArrayView
    extends NativeArrayBufferView
{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**  The length, in elements, of the array. */
    protected final int length;

    /**
     * Instantiates a new native typed array view.
     */
    protected NativeTypedArrayView()
    {
        super();
        length = 0;
    }

    /**
     * Instantiates a new native typed array view.
     *
     * @param ab the ab
     * @param off the off
     * @param len the len
     * @param byteLen the byte len
     */
    protected NativeTypedArrayView(NativeArrayBuffer ab, int off, int len, int byteLen)
    {
        super(ab, off, byteLen);
        length = len;
    }

    // Array properties implementation

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ScriptableObject#get(int, org.mozilla.javascript.Scriptable)
     */
    @Override
    public Object get(int index, Scriptable start)
    {
        return js_get(index);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ScriptableObject#has(int, org.mozilla.javascript.Scriptable)
     */
    @Override
    public boolean has(int index, Scriptable start)
    {
        return ((index > 0) && (index < length));
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ScriptableObject#put(int, org.mozilla.javascript.Scriptable, java.lang.Object)
     */
    @Override
    public void put(int index, Scriptable start, Object val)
    {
        js_set(index, val);
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ScriptableObject#delete(int)
     */
    @Override
    public void delete(int index)
    {
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.ScriptableObject#getIds()
     */
    @Override
    public Object[] getIds()
    {
        Object[] ret = new Object[length];
        for (int i = 0; i < length; i++) {
            ret[i] = Integer.valueOf(i);
        }
        return ret;
    }

    // Actual functions

    /**
     * Check index.
     *
     * @param index the index
     * @return true, if successful
     */
    protected boolean checkIndex(int index)
    {
       return ((index < 0) || (index >= length));
    }

    /**
     * Construct.
     *
     * @param ab the ab
     * @param off the off
     * @param len the len
     * @return the native typed array view
     */
    protected abstract NativeTypedArrayView construct(NativeArrayBuffer ab, int off, int len);
    
    /**
     * Js_get.
     *
     * @param index the index
     * @return the object
     */
    protected abstract Object js_get(int index);
    
    /**
     * Js_set.
     *
     * @param index the index
     * @param c the c
     * @return the object
     */
    protected abstract Object js_set(int index, Object c);
    
    /**
     * Gets the bytes per element.
     *
     * @return the bytes per element
     */
    protected abstract int getBytesPerElement();
    
    /**
     * Real this.
     *
     * @param thisObj the this obj
     * @param f the f
     * @return the native typed array view
     */
    protected abstract NativeTypedArrayView realThis(Scriptable thisObj, IdFunctionObject f);

    /**
     * Make array buffer.
     *
     * @param cx the cx
     * @param scope the scope
     * @param length the length
     * @return the native array buffer
     */
    private NativeArrayBuffer makeArrayBuffer(Context cx, Scriptable scope, int length)
    {
        return (NativeArrayBuffer)cx.newObject(scope, NativeArrayBuffer.CLASS_NAME,
                                               new Object[] { length });
    }

    /**
     * Js_constructor.
     *
     * @param cx the cx
     * @param scope the scope
     * @param args the args
     * @return the native typed array view
     */
    private NativeTypedArrayView js_constructor(Context cx, Scriptable scope, Object[] args)
    {
        if (!isArg(args, 0)) {
            return construct(NativeArrayBuffer.EMPTY_BUFFER, 0, 0);

        } else if ((args[0] instanceof Number) || (args[0] instanceof String)) {
            // Create a zeroed-out array of a certain length
            int length = ScriptRuntime.toInt32(args[0]);
            NativeArrayBuffer buffer = makeArrayBuffer(cx, scope, length * getBytesPerElement());
            return construct(buffer, 0, length);

        } else if (args[0] instanceof NativeTypedArrayView) {
            // Copy elements from the old array and convert them into our own
            NativeTypedArrayView src = (NativeTypedArrayView)args[0];
            NativeArrayBuffer na = makeArrayBuffer(cx, scope, src.length * getBytesPerElement());
            NativeTypedArrayView v = construct(na, 0, src.length);

            for (int i = 0; i < src.length; i++) {
                v.js_set(i, src.js_get(i));
            }
            return v;

        } else if (args[0] instanceof NativeArrayBuffer) {
            // Make a slice of an existing buffer, with shared storage
            NativeArrayBuffer na = (NativeArrayBuffer)args[0];
            int byteOff = isArg(args, 1) ? ScriptRuntime.toInt32(args[1]) : 0;

            int byteLen;
            if (isArg(args, 2)) {
                byteLen = ScriptRuntime.toInt32(args[2]) * getBytesPerElement();
            } else {
                byteLen = na.getLength() - byteOff;
            }

            if ((byteOff < 0) || (byteOff > na.buffer.length)) {
                throw ScriptRuntime.constructError("RangeError", "offset out of range");
            }
            if ((byteLen < 0) || ((byteOff + byteLen) > na.buffer.length)) {
                throw ScriptRuntime.constructError("RangeError", "length out of range");
            }
            if ((byteOff % getBytesPerElement()) != 0) {
                throw ScriptRuntime.constructError("RangeError", "offset must be a multiple of the byte size");
            }
            if ((byteLen % getBytesPerElement()) != 0) {
                throw ScriptRuntime.constructError("RangeError", "offset and buffer must be a multiple of the byte size");
            }

            return construct(na, byteOff, byteLen / getBytesPerElement());

        } else if (args[0] instanceof NativeArray) {
            // Copy elements of the array and convert them to the correct type
            List l = (List)args[0];
            NativeArrayBuffer na = makeArrayBuffer(cx, scope, l.size() * getBytesPerElement());
            NativeTypedArrayView v = construct(na, 0, l.size());
            int p = 0;
            for (Object o : l) {
                v.js_set(p, o);
                p++;
            }
            return v;

        } else {
            throw ScriptRuntime.constructError("Error", "invalid argument");
        }
    }

    /**
     * Sets the range.
     *
     * @param v the v
     * @param off the off
     */
    private void setRange(NativeTypedArrayView v, int off)
    {
        if (off >= length) {
            throw ScriptRuntime.constructError("RangeError", "offset out of range");
        }

        if (v.length > (length - off)) {
            throw ScriptRuntime.constructError("RangeError", "source array too long");
        }

        if (v.arrayBuffer == arrayBuffer) {
            // Copy to temporary space first, as per spec, to avoid messing up overlapping copies
            Object[] tmp = new Object[v.length];
            for (int i = 0; i < v.length; i++) {
                tmp[i] = v.js_get(i);
            }
            for (int i = 0; i < v.length; i++) {
                js_set(i + off, tmp[i]);
            }
        } else {
            for (int i = 0; i < v.length; i++) {
                js_set(i + off, v.js_get(i));
            }
        }
    }

    /**
     * Sets the range.
     *
     * @param a the a
     * @param off the off
     */
    private void setRange(NativeArray a, int off)
    {
        if (off > length) {
            throw ScriptRuntime.constructError("RangeError", "offset out of range");
        }
        if ((off + a.size()) > length) {
            throw ScriptRuntime.constructError("RangeError", "offset + length out of range");
        }

        int pos = off;
        for (Object val : a) {
            js_set(pos, val);
            pos++;
        }
    }

    /**
     * Js_subarray.
     *
     * @param cx the cx
     * @param scope the scope
     * @param s the s
     * @param e the e
     * @return the object
     */
    private Object js_subarray(Context cx, Scriptable scope, int s, int e)
    {
        int start = (s < 0 ? length + s : s);
        int end = (e < 0 ? length + e : e);

        // Clamping behavior as described by the spec.
        start = Math.max(0, start);
        end = Math.min(length, end);
        int len = Math.max(0, (end - start));
        int byteOff = Math.min(start * getBytesPerElement(), arrayBuffer.getLength());

        return
            cx.newObject(scope, getClassName(),
                         new Object[] { arrayBuffer, byteOff, len });
    }

    // Dispatcher

    /* (non-Javadoc)
     * @see org.mozilla.javascript.IdScriptableObject#execIdCall(org.mozilla.javascript.IdFunctionObject, org.mozilla.javascript.Context, org.mozilla.javascript.Scriptable, org.mozilla.javascript.Scriptable, java.lang.Object[])
     */
    @Override
    public Object execIdCall(IdFunctionObject f, Context cx, Scriptable scope,
                             Scriptable thisObj, Object[] args)
    {
        if (!f.hasTag(getClassName())) {
            return super.execIdCall(f, cx, scope, thisObj, args);
        }
        int id = f.methodId();
        switch (id) {
        case Id_constructor:
            return js_constructor(cx, scope, args);

        case Id_get:
            if (args.length > 0) {
                return realThis(thisObj, f).js_get(ScriptRuntime.toInt32(args[0]));
            } else {
                throw ScriptRuntime.constructError("Error", "invalid arguments");
            }

        case Id_set:
            if (args.length > 0) {
                NativeTypedArrayView self = realThis(thisObj, f);
                if (args[0] instanceof NativeTypedArrayView) {
                    int offset = isArg(args, 1) ? ScriptRuntime.toInt32(args[1]) : 0;
                    self.setRange((NativeTypedArrayView)args[0], offset);
                    return Undefined.instance;
                }
                if (args[0] instanceof NativeArray) {
                    int offset = isArg(args, 1) ? ScriptRuntime.toInt32(args[1]) : 0;
                    self.setRange((NativeArray)args[0], offset);
                    return Undefined.instance;
                }
                if (args[0] instanceof Scriptable) {
                    // Tests show that we need to ignore a non-array object
                    return Undefined.instance;
                }
                if (isArg(args, 2)) {
                    return self.js_set(ScriptRuntime.toInt32(args[0]), args[1]);
                }
            }
            throw ScriptRuntime.constructError("Error", "invalid arguments");

        case Id_subarray:
            if (args.length > 0) {
                NativeTypedArrayView self = realThis(thisObj, f);
                int start = ScriptRuntime.toInt32(args[0]);
                int end = isArg(args, 1) ? ScriptRuntime.toInt32(args[1]) : self.length;
                return self.js_subarray(cx, scope, start, end);
            } else {
                throw ScriptRuntime.constructError("Error", "invalid arguments");
            }
        }
        throw new IllegalArgumentException(String.valueOf(id));
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.IdScriptableObject#initPrototypeId(int)
     */
    @Override
    protected void initPrototypeId(int id)
    {
        String s;
        int arity;
        switch (id) {
        case Id_constructor:        arity = 1; s = "constructor"; break;
        case Id_get:                arity = 1; s = "get"; break;
        case Id_set:                arity = 2; s = "set"; break;
        case Id_subarray:           arity = 2; s = "subarray"; break;
        default: throw new IllegalArgumentException(String.valueOf(id));
        }
        initPrototypeMethod(getClassName(), id, s, arity);
    }

    // #string_id_map#

    /* (non-Javadoc)
     * @see org.mozilla.javascript.IdScriptableObject#findPrototypeId(java.lang.String)
     */
    @Override
    protected int findPrototypeId(String s)
    {
        int id;
// #generated# Last update: 2014-12-04 18:21:01 PST
        L0: { id = 0; String X = null; int c;
            int s_length = s.length();
            if (s_length==3) {
                c=s.charAt(0);
                if (c=='g') { if (s.charAt(2)=='t' && s.charAt(1)=='e') {id=Id_get; break L0;} }
                else if (c=='s') { if (s.charAt(2)=='t' && s.charAt(1)=='e') {id=Id_set; break L0;} }
            }
            else if (s_length==8) { X="subarray";id=Id_subarray; }
            else if (s_length==11) { X="constructor";id=Id_constructor; }
            if (X!=null && X!=s && !X.equals(s)) id = 0;
            break L0;
        }
// #/generated#
        return id;
    }

    // Table of all functions
    /** The Constant Id_subarray. */
    private static final int
        Id_constructor          = 1,
        Id_get                  = 2,
        Id_set                  = 3,
        Id_subarray             = 4;

    /** The Constant MAX_PROTOTYPE_ID. */
    protected static final int
        MAX_PROTOTYPE_ID        = Id_subarray;

// #/string_id_map#

    // Constructor properties

    /* (non-Javadoc)
 * @see org.mozilla.javascript.IdScriptableObject#fillConstructorProperties(org.mozilla.javascript.IdFunctionObject)
 */
@Override
    protected void fillConstructorProperties(IdFunctionObject ctor)
    {
        ctor.put("BYTES_PER_ELEMENT", ctor, ScriptRuntime.wrapInt(getBytesPerElement()));
    }

    // Property dispatcher

    /* (non-Javadoc)
     * @see org.mozilla.javascript.typedarrays.NativeArrayBufferView#getMaxInstanceId()
     */
    @Override
    protected int getMaxInstanceId()
    {
        return MAX_INSTANCE_ID;
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.typedarrays.NativeArrayBufferView#getInstanceIdName(int)
     */
    @Override
    protected String getInstanceIdName(int id)
    {
        switch (id) {
        case Id_length: return "length";
        case Id_BYTES_PER_ELEMENT: return "BYTES_PER_ELEMENT";
        default: return super.getInstanceIdName(id);
        }
    }

    /* (non-Javadoc)
     * @see org.mozilla.javascript.typedarrays.NativeArrayBufferView#getInstanceIdValue(int)
     */
    @Override
    protected Object getInstanceIdValue(int id)
    {
        switch (id) {
        case Id_length:
            return ScriptRuntime.wrapInt(length);
        case Id_BYTES_PER_ELEMENT:
            return ScriptRuntime.wrapInt(getBytesPerElement());
        default:
            return super.getInstanceIdValue(id);
        }
    }

// #string_id_map#

    /* (non-Javadoc)
 * @see org.mozilla.javascript.typedarrays.NativeArrayBufferView#findInstanceIdInfo(java.lang.String)
 */
@Override
    protected int findInstanceIdInfo(String s)
    {
        int id;
// #generated# Last update: 2014-12-08 17:33:28 PST
        L0: { id = 0; String X = null;
            int s_length = s.length();
            if (s_length==6) { X="length";id=Id_length; }
            else if (s_length==17) { X="BYTES_PER_ELEMENT";id=Id_BYTES_PER_ELEMENT; }
            if (X!=null && X!=s && !X.equals(s)) id = 0;
            break L0;
        }
// #/generated#
        if (id == 0) {
            return super.findInstanceIdInfo(s);
        }
        return instanceIdInfo(READONLY | PERMANENT, id);
    }

    /*
     * These must not conflict with ids in the parent since we delegate there for property dispatching.
     */
    /** The Constant MAX_INSTANCE_ID. */
    private static final int
        Id_length               = 10,
        Id_BYTES_PER_ELEMENT    = 11,
        MAX_INSTANCE_ID         = Id_BYTES_PER_ELEMENT;

// #/string_id_map#
}
