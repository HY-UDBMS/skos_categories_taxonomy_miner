/**
 * Copyright (c) 2017 UDBMS group, Department of Computer Science, University of Helsinki
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package taxonomy.Etcetera;

import lombok.val;
import org.javatuples.Pair;

import java.io.*;
import java.util.*;

public class SkosCategoriesParser
{
    static HashMap<Integer, List<Integer>> relations = new HashMap<>();
    static HashMap<String, Pair<Integer, String>> labels = new HashMap<>();

    public static void main(String[] args) throws IOException
    {
        BufferedReader reader = new BufferedReader(new FileReader("skos_categories_en.ttl"));

        String line = "";

        // read all labels
        while ((line = reader.readLine()) != null)
        {
            if (line.startsWith("#"))
                continue;

            val ele = line.split(">\\s");
            if (ele.length < 3)
                continue;

            if (!ele[1].equals("<http://www.w3.org/2004/02/skos/core#prefLabel"))
                continue;

            addLabel(ele[0], ele[2], false);
        }

        reader.close();
        reader = new BufferedReader(new FileReader("skos_categories_en.ttl"));

        // then read all relations
        while ((line = reader.readLine()) != null)
        {
            if (line.startsWith("#"))
                continue;

            val ele = line.split(">\\s");
            if (ele.length < 3)
                continue;

            if (!ele[1].equals("<http://www.w3.org/2004/02/skos/core#broader"))
                continue;

            addRelation(ele[0], ele[2]);
        }

        val writer = new BufferedWriter(new FileWriter("wiki_cat.txt"));
        val cat = new ArrayList<Pair<Integer, String>>(labels.size());
        labels.values().forEach(cat::add);
        cat.sort(Comparator.comparingInt(Pair::getValue0));
        cat.forEach(c ->
        {
            try
            {
                writer.write(String.format("%d\t%s\n", c.getValue0(), c.getValue1()));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });
        writer.close();

        val writer2 = new BufferedWriter(new FileWriter("wiki_relation.txt"));
        val rel = new ArrayList<Map.Entry<Integer, List<Integer>>>(relations.size());
        relations.entrySet().forEach(r -> rel.add(r));
        rel.sort(Comparator.comparingInt(Map.Entry::getKey));
        rel.forEach(r ->
        {
            r.getValue().sort(Comparator.comparingInt(a -> a));
            r.getValue().forEach(v ->
            {
                try
                {
                    writer2.write(String.format("%d\t%d\n", r.getKey(), v));
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            });
        });
        writer2.close();

        System.out.println();
    }

    private static void addRelation(String child, String parent)
    {
        if (labels.get(child) == null)
            addLabel(child, child.substring(38), true);
        if (labels.get(parent) == null)
            addLabel(parent, parent.substring(38), true);

        val id_child = labels.get(child).getValue0();
        val id_parent = labels.get(parent).getValue0();

        if (!relations.containsKey(id_parent))
            relations.put(id_parent, new ArrayList<>());

        relations.get(id_parent).add(id_child);
    }

    private static void addLabel(String node, String label, boolean missing_label)
    {
        val id = labels.size() + 1;

        if (labels.containsKey(node))
            System.out.println(node);

        labels.put(node, new Pair<>(id, missing_label ? label : label.substring(1, label.length() - 6)));
    }
}
