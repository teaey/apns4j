package cn.teaey.apns4j.protocol;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * User: Teaey
 * Date: 13-8-31
 *
 * @author xiaofei.wxf
 * @version $Id: $Id
 */
public class ListMap<K, V> extends AbstractMap<K, V> implements Map<K, V>
{
    Entry[] table;
    int     size;
    /**
     * <p>Constructor for ListMap.</p>
     */
    public ListMap()
    {
        table = new Entry[16];
    }
    /**
     * <p>Constructor for ListMap.</p>
     *
     * @param initialCapacity a int.
     */
    public ListMap(int initialCapacity)
    {
        table = new Entry[initialCapacity];
    }
    private Entry remove(int index)
    {
        rangeCheck(index);
        Entry oldValue = table[index];
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(table, index + 1, table, index, numMoved);
        table[--size] = null;
        return oldValue;
    }
    private void rangeCheck(int index)
    {
        if (index >= size)
        {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }
    private String outOfBoundsMsg(int index)
    {
        return "Index: " + index + ", Size: " + size;
    }
    static class Entry<K, V> implements Map.Entry<K, V>
    {
        final K key;
        V value;
        Entry(K key, V value)
        {
            this.key = key;
            this.value = value;
        }
        @Override
        public K getKey()
        {
            return key;
        }
        @Override
        public V getValue()
        {
            return value;
        }
        @Override
        public V setValue(V value)
        {
            V oldValue = value;
            this.value = value;
            return oldValue;
        }
        public String toString()
        {
            return "[" + getKey() + ":" + getValue() + "]";
        }
    }
    private Entry index(int index)
    {
        rangeCheck(index);
        return table[index];
    }
    private abstract class ListIterator<K, V> implements Iterator<Map.Entry<K, V>>
    {
        int index;
        ListIterator()
        {
        }
        @Override
        public boolean hasNext()
        {
            if (index == (size() - 1))
            {
                return false;
            }
            else
            {
                return true;
            }
        }
        @Override
        public Map.Entry<K, V> next()
        {
            return index(++index);
        }
        @Override
        public void remove()
        {
            ListMap.this.remove(index);
        }
    }
    /** {@inheritDoc} */
    @Override
    public Set<Map.Entry<K, V>> entrySet()
    {
        return null;
    }
}
