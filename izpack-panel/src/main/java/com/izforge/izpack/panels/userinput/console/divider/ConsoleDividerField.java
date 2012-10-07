/*
 * IzPack - Copyright 2001-2012 Julien Ponge, All Rights Reserved.
 *
 * http://izpack.org/
 * http://izpack.codehaus.org/
 *
 * Copyright 2012 Tim Anderson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.izforge.izpack.panels.userinput.console.divider;

import com.izforge.izpack.api.handler.Prompt;
import com.izforge.izpack.panels.userinput.console.ConsoleField;
import com.izforge.izpack.panels.userinput.rule.Field;
import com.izforge.izpack.util.Console;

/**
 * Console divider field.
 *
 * @author Tim Anderson
 */
public class ConsoleDividerField extends ConsoleField
{

    /**
     * Constructs a {@link com.izforge.izpack.panels.userinput.console.ConsoleField}.
     *
     * @param field   the field
     * @param console the console
     */
    public ConsoleDividerField(Field field, Console console, Prompt prompt)
    {
        super(field, console, prompt);
    }

    /**
     * Displays the field.
     * <p/>
     * For fields that update variables, this collects input and validates it.
     *
     * @return {@code true} if the field was displayed and validated successfully
     */
    @Override
    public boolean display()
    {
        println("------------------------------------------");
        return true;
    }
}
