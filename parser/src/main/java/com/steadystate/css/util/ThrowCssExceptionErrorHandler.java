/*
 * Copyright (C) 1999-2017 David Schweinsberg.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.steadystate.css.util;

import java.io.Serializable;

import org.w3c.css.sac.CSSParseException;
import org.w3c.css.sac.ErrorHandler;

/**
 * Helper implementation of {@link ErrorHandler}, which throws CssException in case of problems.
 *
 * @author rbri
 * @see ErrorHandler
 */
public class ThrowCssExceptionErrorHandler implements ErrorHandler, Serializable {
    private static final long serialVersionUID = -3933638774901855095L;

    /**
     * Singleton.
     */
    public static final ThrowCssExceptionErrorHandler INSTANCE = new ThrowCssExceptionErrorHandler();

    /**
     * {@inheritDoc}
     */
    public void error(final CSSParseException exception) {
        throw exception;
    }

    /**
     * {@inheritDoc}
     */
    public void fatalError(final CSSParseException exception) {
        throw exception;
    }

    /**
     * {@inheritDoc}
     */
    public void warning(final CSSParseException exception) {
        // ignore warnings
    }
}
