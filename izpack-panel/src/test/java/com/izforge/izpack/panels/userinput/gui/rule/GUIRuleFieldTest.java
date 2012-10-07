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

package com.izforge.izpack.panels.userinput.gui.rule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.izforge.izpack.api.factory.ObjectFactory;
import com.izforge.izpack.api.rules.RulesEngine;
import com.izforge.izpack.core.container.DefaultContainer;
import com.izforge.izpack.core.data.DefaultVariables;
import com.izforge.izpack.core.factory.DefaultObjectFactory;
import com.izforge.izpack.core.rules.ConditionContainer;
import com.izforge.izpack.core.rules.RulesEngineImpl;
import com.izforge.izpack.installer.data.GUIInstallData;
import com.izforge.izpack.panels.userinput.rule.rule.RuleField;
import com.izforge.izpack.panels.userinput.rule.rule.RuleFormat;
import com.izforge.izpack.panels.userinput.rule.rule.TestDefaultIPProcessor;
import com.izforge.izpack.panels.userinput.processor.Processor;
import com.izforge.izpack.util.Platforms;


/**
 * Tests the {@link GUIRuleField}.
 *
 * @author Tim Anderson
 */
public class GUIRuleFieldTest
{

    /**
     * The install data.
     */
    private GUIInstallData installData;

    /**
     * The object factory.
     */
    private ObjectFactory factory;


    /**
     * Default constructor.
     */
    public GUIRuleFieldTest()
    {
        installData = new GUIInstallData(new DefaultVariables(), Platforms.HP_UX);
        RulesEngine rules = new RulesEngineImpl(new ConditionContainer(new DefaultContainer()),
                                                installData.getPlatform());
        installData.setRules(rules);
        factory = new DefaultObjectFactory(new DefaultContainer());
    }

    /**
     * Tests support for entering IP addresses.
     */
    @Test
    public void testIPAddress()
    {
        String layout = "N:3:3 . N:3:3 . N:3:3 . N:3:3"; // IP address format
        String set = "0:192 1:168 2:0 3:1";              // default value
        String separator = null;

        String variable = "variable1";
        RuleField model = new RuleField(variable, layout, RuleFormat.DISPLAY_FORMAT, set, separator, null, null,
                                        null, null, installData, factory);

        GUIRuleField field = new GUIRuleField(model);
        assertFalse(field.updateView());               // should be nothing to update

        // check default value
        assertEquals("192.168.0.1", field.getValue());

        String[] values = field.getValues();
        assertEquals(4, values.length);
        assertEquals("192", values[0]);
        assertEquals("168", values[1]);
        assertEquals("0", values[2]);
        assertEquals("1", values[3]);

        assertTrue(field.updateField());

        assertEquals("192.168.0.1", installData.getVariable(variable));

        field.setValues("127", "0", "0", "1");
        assertTrue(field.updateField());
        assertEquals("127.0.0.1", installData.getVariable(variable));

        // the following is a bit ridiculous but highlights that a minimum length can't be specified for a field
        field.setValues("", "", "", "");
        assertTrue(field.updateField());
        assertEquals("...", installData.getVariable(variable));
    }

    /**
     * Tests the specification of a {@link Processor} as part of the 'set' attribute.
     */
    @Test
    public void testDefaultValueProcessor()
    {
        String layout = "N:3:3 . N:3:3 . N:3:3 . N:3:3"; // IP address format
        String set = "0::" + TestDefaultIPProcessor.class.getName(); // The processor will be run for the first field
        String variable = "variable1";
        String separator = null;
        RuleField model = new RuleField(variable, layout, RuleFormat.DISPLAY_FORMAT, set, separator, null, null,
                                        null, null, installData, factory);
        GUIRuleField field = new GUIRuleField(model);
        assertEquals("192.168.0.1", field.getValue());

        assertNull(installData.getVariable("variable1"));
        assertTrue(field.updateField());
        assertEquals("192.168.0.1", installData.getVariable(variable));
    }
}
