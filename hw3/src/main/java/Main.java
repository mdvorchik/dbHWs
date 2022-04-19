import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Protocol;
import redis.clients.jedis.UnifiedJedis;
import redis.clients.jedis.providers.PooledConnectionProvider;

import java.util.*;

public class Main {
    public static final String JSON_STRING = "json_string";
    public static final String JSON_HASH = "json_hash";
    public static final String JSON_ZSET = "json_zset";
    public static final String JSON_LIST = "json_list";

    public static void main(String[] args) {
        HostAndPort config = new HostAndPort(Protocol.DEFAULT_HOST, 12000);
        PooledConnectionProvider provider = new PooledConnectionProvider(config);
        UnifiedJedis client = new UnifiedJedis(provider);

        PersonGenerator personGenerator = new PersonGenerator();
        List<Person> people = new ArrayList<>();
        List<String> peopleStr = new ArrayList<>();
        List<Map<String, String>> peopleMap = new ArrayList<>();
        Map<String, Double> peopleScoreMap = new HashMap<>();
        for (int i = 0; i < 60000; i++) {
            Person person = personGenerator.generatePerson(i);
            people.add(person);
            peopleStr.add(person.toString());
            peopleScoreMap.put(person.toString(), (double) i);
            Map<String, String> props = new HashMap<>();
            props.put("id", person.getId());
            props.put("address", person.getAddress());
            props.put("balance", person.getBalance());
            props.put("gender", person.getGender());
            props.put("name", person.getName());
            props.put("age", person.getAge().toString());
            peopleMap.add(props);
        }
        StringBuilder allPeopleStr = new StringBuilder();
        for (String s : peopleStr) {
            allPeopleStr.append(s);
        }

        Long date1 = new Date().getTime();
        writeString(client, allPeopleStr.toString());
        Long date11 = new Date().getTime();
        System.out.println(readString(client));
        Long date111 = new Date().getTime();

        Long date2 = new Date().getTime();
        writeList(client, peopleStr.toArray(new String[0]));
        Long date22 = new Date().getTime();
        System.out.println(readList(client));
        Long date222 = new Date().getTime();

        Long date3 = new Date().getTime();
        writeZset(client, peopleScoreMap);
        Long date33 = new Date().getTime();
        System.out.println(readZset(client));
        Long date333 = new Date().getTime();

//        Long date4 = new Date().getTime();
//        writeHash(client, peopleMap);
//        Long date44 = new Date().getTime();
//        System.out.println(readHash(client));
//        Long date444 = new Date().getTime();


        System.out.println("writeString Time: " + (date11 - date1));
        System.out.println("readString Time: " + (date111 - date11));

        System.out.println("writeList Time: " + (date22 - date2));
        System.out.println("readList Time: " + (date222 - date22));

        System.out.println("writeZset Time: " + (date33 - date3));
        System.out.println("readZset Time: " + (date333 - date33));

//        System.out.println("writeHash Time: " + (date44 - date4));
//        System.out.println("readHash Time: " + (date444 - date44));

//        delKey(client, JSON_LIST);
//        delKey(client, JSON_STRING);
//        delKey(client, JSON_ZSET);
//
//        for (int i = 0; i < 60000; i++) {
//            delKey(client, JSON_HASH + ":" + i);
//        }

    }

    private static void writeString(UnifiedJedis client, String people) {
        client.append(JSON_STRING, people);
    }

    private static String readString(UnifiedJedis client) {
        return client.get(JSON_STRING);
    }

    private static void writeList(UnifiedJedis client, String... people) {
        client.lpush(JSON_LIST, people);
    }

    private static List<String> readList(UnifiedJedis client) {
        return client.lrange(JSON_LIST, 0, -1);
    }

    private static void writeZset(UnifiedJedis client, Map<String, Double> people) {
        client.zadd(JSON_ZSET, people);
    }

    private static List<String> readZset(UnifiedJedis client) {
        return client.zrange(JSON_ZSET, 0, -1);
    }

    private static void writeHash(UnifiedJedis client, List<Map<String, String>> people) {
        for (int i = 0; i < people.size(); i++) {
            client.hmset(JSON_HASH + ":" + i, people.get(i));
        }

    }

    private static String readHash(UnifiedJedis client) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 60000; i++) {
            stringBuilder.append(client.hvals(JSON_HASH + i));
        }
        return stringBuilder.toString();
    }

    private static void delKey(UnifiedJedis client, String key) {
        client.del(key);
    }


}
