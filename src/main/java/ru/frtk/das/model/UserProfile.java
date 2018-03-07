package ru.frtk.das.model;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import java.util.Map;
import java.util.UUID;

@Entity
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
})
@Table(name = "user_profile")
public class UserProfile {
    @Id
    @Column(name = "id")
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Type(type = "jsonb")
    @Column(name = "additional_attributes", columnDefinition = "json")
    private Map<ModelAttribute, Object> additionalAttributes;

    // TODO: Write mandatory user data
}
