package com.idk.diary.model;

import io.swagger.annotations.ApiModel;
import lombok.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.time.Instant;

@ApiModel(
        description = "Main domain entity"
)
@Entity
@Table(name = "diaries")
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data // TODO this is not recommended to use in JPA entities!!!
@EqualsAndHashCode(exclude = {"createdAt", "updatedAt"}) // TODO this is not recommended to use in JPA entities!!!
public class Diary {

    @EmbeddedId
    @AttributeOverride(name = "idValue", column = @Column(name  = "id"))
    private DiaryId id;

    private String name;
    private String text;
    private String location;

    @Generated(value = GenerationTime.INSERT)
    private Instant createdAt;

    @Generated(value = GenerationTime.ALWAYS)
    private Instant updatedAt;

    @Version
    private Integer version;

}
