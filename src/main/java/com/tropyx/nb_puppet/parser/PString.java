/*
 * Copyright (C) 2014 mkleint
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.tropyx.nb_puppet.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PString extends PElement {
    private String value;
    final static Pattern VAR = Pattern.compile("\\$\\{([a-zA-Z_:]+?)\\}");
    
    public PString(PElement parent, int offset, String value) {
        super(STRING, parent, offset);
        this.value = value;
        if (this.value.startsWith("\"") && this.value.endsWith("\"")) {
            this.value = this.value.substring(1, this.value.length() - 1);
            Matcher m = VAR.matcher(this.value);
            while (m.find()) {
                String var = m.group(1);
                new PVariable(this, offset + m.start(), "$" + var);
            }
        }
        if (this.value.startsWith("'") && this.value.endsWith("'")) {
            this.value = this.value.substring(1, this.value.length() - 1);
        }
    }

    public String getValue() {
        return value;
    }

    void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return super.toString() + "[" +  value +  ']';
    }
    
}
