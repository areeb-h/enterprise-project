package com.example.enterpriseproject.audit;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> {

    @CreatedBy
    protected U createdBy;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    protected U createdDate;

    @LastModifiedBy
    protected U lastModifiedBy;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    protected U lastModifiedDate;

    public U getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(U createdBy) {
        this.createdBy = createdBy;
    }

    public U getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(U lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public U getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(U createdDate) {
        this.createdDate = createdDate;
    }

    public U getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(U lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

}
