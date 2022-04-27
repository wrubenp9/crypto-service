package br.com.crypto.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable=false)
    private String nameCrypto;

    @Column(nullable=false)
    private String code;

    @CreationTimestamp
    private OffsetDateTime createdAt;

    public Currency(){
    }

    public Currency(String nameCrypto, String code){
        this.nameCrypto = nameCrypto;
        this.code = code;
    }
}
