package org.acme.dao;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.acme.EntityID;
import org.acme.avro.Unit;
import org.acme.entity.Barracks;
import org.acme.entity.CommandCenter;
import org.springframework.stereotype.Component;


@Component
public class TAtest {

    private final TableAccess<Barracks> build;

    private final TableAccess<CommandCenter> cbulid;


    public TAtest(TableAccess<Barracks> build, TableAccess<CommandCenter> cbulid) {
        this.build = build;
        this.cbulid = cbulid;
    }


    final static AtomicInteger at = new AtomicInteger();


    @Transactional
    public String create(String name) {


        Barracks marine = new Barracks();
        Barracks fire = new Barracks();
        UUID uuid = UUID.randomUUID();
        int id = Integer.parseInt(at.incrementAndGet() + "");
        marine.setId(id);
        marine.setName(name + id);

        fire.setId(Integer.parseInt(at.incrementAndGet() + ""));
        fire.setName(name);

        build.save(marine);
        build.save(fire);
        EntityID i;

        i = new Barracks();
        i.getId();
        i.getName();

        i = new CommandCenter();


        CommandCenter c = new CommandCenter();
        c.setId(Integer.parseInt(at.incrementAndGet() + ""));
        c.setName(name + "name");
        cbulid.save(c);





        return "";
    }


    public String find(int id) {

        return cbulid.find(id).getName();
    }


    private Unit deserializeUser(byte[] avroData) throws IOException {
        // Avro 역직렬화 로직 구현 (여기서는 예시로 단순화)
        // 실제 구현에서는 Avro 스키마를 사용하여 데이터 변환을 해야 합니다.
        return new Unit(); // 단순화된 예시
    }


    // User 객체를 Avro 데이터로 직렬화하는 메서드
    private byte[] serializeUser(Unit unit) throws IOException {
        // Avro 직렬화 로직 구현 (여기서는 예시로 단순화)
        // 실제 구현에서는 Avro 스키마를 사용하여 데이터를 직렬화해야 합니다.
        return new byte[0]; // 단순화된 예시
    }


}
