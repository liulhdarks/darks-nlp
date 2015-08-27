/**
 * 
 * Copyright 2015 The Darks NLP Project (Liu lihua)
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
package darks.nlp.utils;

import java.util.Collection;

public final class StringUtils
{

	private StringUtils()
	{
		
	}
	
	public static String stringBuffer(Object... objs)
	{
		StringBuilder buf = new StringBuilder();
		for (Object obj : objs)
		{
			buf.append(obj);
		}
		return buf.toString();
	}
	
	public static String toCollectionString(Collection<?> c, char sep)
	{
		StringBuilder buf = new StringBuilder();
		for (Object obj : c)
		{
			buf.append(obj).append(sep);
		}
		if (buf.length() > 0)
		{
			buf.setLength(buf.length() - 1);
		}
		return buf.toString();
	}
}
