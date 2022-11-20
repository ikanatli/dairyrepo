package com.idk.diary.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.time.Instant;

@Schema(description = "Main domain entity")
@Entity
@Table(name = "diaries")
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data // TODO this is not recommended to use in JPA entities!!!
@EqualsAndHashCode(exclude = {"createdAt", "updatedAt"}) // TODO this is not recommended to use in JPA entities!!!
public class Diary {

    @EmbeddedId
    @AttributeOverride(name = "idValue", column = @Column(name = "id"))
    private DiaryId id;

    @NotBlank(message = "Name must not be empty.")
    @Length(min = 4, max = 40, message = "Name length must not be at least 4 and at most 40 characters.")
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
