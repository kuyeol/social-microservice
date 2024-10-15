package com.packt.cantata.temp.board;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "BOARD_CATEGORY")
public class BoardCategoryEntity {

    @Id
    @Column(name = "ROLE_ID")
    protected String roleId;

    @Id
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="BOARD_ID")
    private BoardEntity boardId;



    // TODO : 권한추가

    public static class Key implements Serializable {

        protected BoardEntity boardId;

        protected String roleId;

        public Key() {
        }

        public Key(BoardEntity user, String roleId) {
            this.boardId = user;
            this.roleId = roleId;
        }

        public BoardEntity getUser() {
            return boardId;
        }

        public String getRoleId() {
            return roleId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Key key = (Key) o;

            if (!roleId.equals(key.roleId)) return false;
            if (!boardId.equals(key.boardId)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = boardId.hashCode();
            result = 31 * result + roleId.hashCode();
            return result;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof  BoardCategoryEntity)) return false;

        BoardCategoryEntity key = ( BoardCategoryEntity) o;

        if (!roleId.equals(key.roleId)) return false;
        if (!boardId.equals(key.boardId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = boardId.hashCode();
        result = 31 * result + roleId.hashCode();
        return result;
    }

    //
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long id; // 카테고리 ID
    //
    //
    //@OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "BOARD")
    //@JoinColumn(name="BOARD_ID")
    //private BoardEntity boards;
    //
    //public Long getId() {
    //    return id;
    //}
    //
    //public void setId(Long id) {
    //    this.id = id;
    //}



}
