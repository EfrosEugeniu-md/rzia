package ru.list.sorfe.rzia.util;

import ru.list.sorfe.rzia.beans.consumer.AggregatesOfConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import ru.list.sorfe.rzia.beans.consumer.Cabel;
import ru.list.sorfe.rzia.beans.consumer.Consumer;
import ru.list.sorfe.rzia.beans.consumer.Provider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static ru.list.sorfe.rzia.util.CommonUtil.*;

public class ConsumerUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerUtil.class.getName());

    public static List<Cabel> cabels = new ArrayList<>();
    public static List<Consumer> consumers = new ArrayList<>();
    public static List<Provider> providers = new ArrayList<>();

    static {
        InputStream resource = null;
        try {
            resource = new ClassPathResource(
                    "baza.txt").getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert resource != null;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] elements = line.split("\\t");
                if (elements.length > 2) {
                    //                   TpIc tpIc = ConvertToTpIc(elements[1]);
                    cabels.add(getCabel(elements));
                    consumers.add(getConsumer(elements));
                    providers.add(getProvider(elements)
                    );
                }
            }
            int a = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Provider getProvider(String[] elements) {
        return new Provider(
                ConvertToTpIc(elements[1]),
                ParseInt(elements[2]),
                ParseInt(elements[3]),
                ParseDouble(elements[4]),
                ParseInt(elements[5]),
                ParseDouble(elements[6]),
                ParseDouble(elements[7])
        );
    }

    private static Consumer getConsumer(String[] elements) {
        int a = 0;
        return new Consumer(
                ConvertToTpIc(elements[1]),
                ConvertToTpIc(elements[10]),
                ParseDouble(elements[11]),
                ParseDouble(elements[12]),
                ConvertToAgr(elements[13]),
                ParseDouble(elements[14])
        );
    }

    private static Cabel getCabel(String[] elements) {
        return new Cabel(
                ConvertToTpIc(elements[1]),
                ParseInt(elements[8]),
                ParseInt(elements[9])
        );
    }

    public static AggregatesOfConsumer ConvertToAgr(String s) {
        String input = s.replace("\u0000", "");
        int index = lowestIndexOf(input, "X");
        String n = (index > 0) ? input.substring(0, index) : s;
        String i = (index > 0) ? input.substring(index + 1) : "";
        return new AggregatesOfConsumer(ParseInt(n), ParseInt(i));
    }

}
