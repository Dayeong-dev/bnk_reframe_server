package com.example.reframe.repository;

import com.example.reframe.dto.report.UserFilter;
import com.example.reframe.dto.report.UserRow;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ReportUserRepository {

  @PersistenceContext private EntityManager em;

  public List<UserRow> find(UserFilter f) {
    StringBuilder jpql = new StringBuilder("""
      select new com.example.reframe.dto.report.UserRow(
        u.username, u.email, u.phone, u.usertype, u.role
      )
      from User u
      where 1=1
    """);
    Map<String,Object> p = new HashMap<>();
    if (f != null) {
      if (f.usertype()!=null && !f.usertype().isBlank()) { jpql.append(" and u.usertype = :us "); p.put("us", f.usertype()); }
      if (f.role()!=null && !f.role().isBlank())         { jpql.append(" and u.role = :r ");     p.put("r", f.role()); }
      if (f.keyword()!=null && !f.keyword().isBlank())   { jpql.append(" and (u.username like :kw or u.email like :kw or u.phone like :kw) "); p.put("kw","%"+f.keyword()+"%"); }
    }
    jpql.append(" order by u.username asc ");
    var q = em.createQuery(jpql.toString(), UserRow.class);
    p.forEach(q::setParameter);
    return q.getResultList();
  }
}
