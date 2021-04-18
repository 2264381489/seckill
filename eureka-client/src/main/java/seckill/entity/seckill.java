package seckill.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "seckill")
public class seckill implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seckill_id", nullable = false)
    private long seckillId;
    private String name;
    private int number;
    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp createTime;
    @Version
    private int version;




}
