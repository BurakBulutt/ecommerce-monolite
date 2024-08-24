package com.examplesoft.ecommercemonolite.entitylistener.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(EntityChanges.TABLE)
@EntityListeners(AuditingEntityListener.class)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class EntityChanges {
    public static final String TABLE = "entity_changes";
    private static final String COL_ID = "id";
    private static final String COL_CREATED_AT = "created_at";
    private static final String COL_UPDATED_AT = "updated_at";
    private static final String COL_PROCESS_TYPE = "process_type";
    private static final String COL_CLASS_NAME = "class_name";
    private static final String COL_CLASS_ID = "class_id";
    private static final String COL_USER_ID = "user_id";

    public EntityChanges(ProcessType processType, String className,String classId,String userId) {
        this.processType = processType;
        this.className = className;
        this.userId = userId;
        this.classId = classId;
    }

    @Id
    @Column(name = COL_ID, nullable = false, updatable = false)
    private String id;

    @Column(name = COL_CREATED_AT, nullable = false)
    @CreatedDate
    private String createdAt;

    @Column(name = COL_UPDATED_AT)
    @LastModifiedDate
    private String updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = COL_PROCESS_TYPE)
    private ProcessType processType;

    @Column(name = COL_CLASS_NAME)
    private String className;

    @Column(name = COL_CLASS_ID)
    private String classId;

    @Column(name = COL_USER_ID)
    private String userId;
}
