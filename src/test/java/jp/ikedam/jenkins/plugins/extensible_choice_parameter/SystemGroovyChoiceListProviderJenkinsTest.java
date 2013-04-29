/*
 * The MIT License
 * 
 * Copyright (c) 2013 IKEDA Yasuyuki
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package jp.ikedam.jenkins.plugins.extensible_choice_parameter;

import hudson.util.ListBoxModel;

import java.util.Arrays;
import java.util.List;

import jenkins.model.Jenkins;

import org.jvnet.hudson.test.ExtensiableChoiceParameterJenkinsTestCase;

/**
 * Tests for SystemGroovyChoiceListProvider, corresponding to Jenkins.
 */
public class SystemGroovyChoiceListProviderJenkinsTest extends ExtensiableChoiceParameterJenkinsTestCase
{
    static private String properScript = "[\"a\", \"b\", \"c\"]";
    static private List<String> properScriptReturn = Arrays.asList("a", "b", "c");
    
    static private String emptyListScript = "def ret = []";
    
    static private String nullScript = "null;";
    
    static private String emptyScript = "";
    
    static private String blankScript = "  ";
    
    static private String syntaxBrokenScript = "1abc = []";
    
    static private String exceptionScript = "hogehoge()";
    
    protected SystemGroovyChoiceListProvider.DescriptorImpl getDescriptor()
    {
        return (SystemGroovyChoiceListProvider.DescriptorImpl)Jenkins.getInstance().getDescriptorOrDie(SystemGroovyChoiceListProvider.class);
    }
    
    public void testDescriptor_doFillDefaultChoiceItems()
    {
        SystemGroovyChoiceListProvider.DescriptorImpl descriptor = getDescriptor();
        
        // Proper script
        {
            ListBoxModel ret = descriptor.doFillDefaultChoiceItems(properScript);
            assertEquals("Script returned an unexpected list", properScriptReturn.size() + 1, ret.size());
            for(int i = 0; i < properScriptReturn.size(); ++i)
            {
                assertEquals("Script returned an unexpected list", properScriptReturn.get(i), ret.get(i + 1).value);
            }
        }
        
        // Empty list script
        {
            ListBoxModel ret = descriptor.doFillDefaultChoiceItems(emptyListScript);
            assertEquals("Script must return an empty list", 1, ret.size());
        }
        
        // Null script
        {
            ListBoxModel ret = descriptor.doFillDefaultChoiceItems(nullScript);
            assertEquals("Script with null must return an empty list", 1, ret.size());
        }
        
        // emptyScript
        {
            ListBoxModel ret = descriptor.doFillDefaultChoiceItems(emptyScript);
            assertEquals("empty script must return an empty list", 1, ret.size());
        }
        
        // blankScript
        {
            ListBoxModel ret = descriptor.doFillDefaultChoiceItems(blankScript);
            assertEquals("blank script must return an empty list", 1, ret.size());
        }
        
        // Syntax broken script
        {
            ListBoxModel ret = descriptor.doFillDefaultChoiceItems(syntaxBrokenScript);
            assertEquals("Syntax-broken-script must return an empty list", 1, ret.size());
        }
        
        // exceptionScript
        {
            ListBoxModel ret = descriptor.doFillDefaultChoiceItems(exceptionScript);
            assertEquals("Script throwing an exception must return an empty list", 1, ret.size());
        }
        
        // null
        {
            ListBoxModel ret = descriptor.doFillDefaultChoiceItems(null);
            assertEquals("null must return an empty list", 1, ret.size());
        }
    }
    
    public void testGetChoiceList()
    {
        // Proper script
        {
            SystemGroovyChoiceListProvider target = new SystemGroovyChoiceListProvider(
                    properScript,
                    null
            );
            List<String> ret = target.getChoiceList();
            assertEquals("Script returned an unexpected list", properScriptReturn.size(), ret.size());
            for(int i = 0; i < properScriptReturn.size(); ++i)
            {
                assertEquals("Script returned an unexpected list", properScriptReturn.get(i), ret.get(i));
            }
        }
        
        // Empty list script
        {
            SystemGroovyChoiceListProvider target = new SystemGroovyChoiceListProvider(
                    emptyListScript,
                    null
            );
            List<String> ret = target.getChoiceList();
            assertEquals("Script must return an empty list", 0, ret.size());
        }
        
        // Null script
        {
            SystemGroovyChoiceListProvider target = new SystemGroovyChoiceListProvider(
                    nullScript,
                    null
            );
            List<String> ret = target.getChoiceList();
            assertEquals("Script with null must return an empty list", 0, ret.size());
        }
        
        // Empty script
        {
            SystemGroovyChoiceListProvider target = new SystemGroovyChoiceListProvider(
                    emptyScript,
                    null
            );
            List<String> ret = target.getChoiceList();
            assertEquals("Empty script must return an empty list", 0, ret.size());
        }
        
        // Blank script
        {
            SystemGroovyChoiceListProvider target = new SystemGroovyChoiceListProvider(
                    blankScript,
                    null
            );
            List<String> ret = target.getChoiceList();
            assertEquals("Blank script must return an empty list", 0, ret.size());
        }
        
        // Syntax broken script
        {
            SystemGroovyChoiceListProvider target = new SystemGroovyChoiceListProvider(
                    syntaxBrokenScript,
                    null
            );
            List<String> ret = target.getChoiceList();
            assertEquals("Syntax-broken-script must return an empty list", 0, ret.size());
        }
        
        // exceptionScript
        {
            SystemGroovyChoiceListProvider target = new SystemGroovyChoiceListProvider(
                    exceptionScript,
                    null
            );
            List<String> ret = target.getChoiceList();
            assertEquals("Script throwing an exception must return an empty list", 0, ret.size());
        }
        
        // null
        {
            SystemGroovyChoiceListProvider target = new SystemGroovyChoiceListProvider(
                    null,
                    null
            );
            List<String> ret = target.getChoiceList();
            assertEquals("null must return an empty list", 0, ret.size());
        }
    }
}
