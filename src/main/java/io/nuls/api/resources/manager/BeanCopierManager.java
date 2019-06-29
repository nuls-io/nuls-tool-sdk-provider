/**
 * MIT License
 * <p>
 * Copyright (c) 2017-2018 nuls.io
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.nuls.api.resources.manager;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;
import org.checkerframework.checker.units.qual.K;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * @author: PierreLuo
 * @date: 2019-06-29
 */
public class BeanCopierManager {
    private BeanCopierManager(){}

    private static final ConcurrentHashMap<String, BeanCopier> BEAN_COPIER_MAP = new ConcurrentHashMap<>();

    public static void beanCopier(String key, Object src, Object target) {
        BeanCopier beanCopier = BEAN_COPIER_MAP.computeIfAbsent(key, k -> initBean(src.getClass(), target.getClass()));
        beanCopier.copy(src, target, null);
    }

    public static BeanCopier initBean(Class src, Class target) {
        BeanCopier beanCopier = BeanCopier.create(src, target, false);
        return beanCopier;
    }
}
