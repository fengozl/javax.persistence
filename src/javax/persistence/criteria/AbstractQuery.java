/*******************************************************************************
 * Copyright (c) 1998, 2009 Oracle. All rights reserved. 
 * 
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0 
 * which accompanies this distribution. 
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * The API for this class and its comments are derived from the JPA 2.0 specification 
 * which is developed under the Java Community Process (JSR 317) and is copyright 
 * Sun Microsystems, Inc. 
 *
 * Contributors:
 *     dclarke - Java Persistence 2.0 - Proposed Final Draft (March 13, 2009)
 *     			 Specification and licensing terms available from
 *     		   	 http://jcp.org/en/jsr/detail?id=317
 *
 * EARLY ACCESS - PUBLIC DRAFT
 * This is an implementation of an early-draft specification developed under the 
 * Java Community Process (JCP) and is made available for testing and evaluation 
 * purposes only. The code is not compatible with any specification of the JCP.
 ******************************************************************************/
package javax.persistence.criteria;

import java.util.List;
import java.util.Set;
import javax.persistence.metamodel.Entity;

/**
 * The interface AbstractQuery defines functionality that is common to both
 * top-level queries and subqueries. It is not intended to be used directly in
 * query construction.
 * 
 * All queries must have: a set of root entities (which may in turn own joins)
 * All queries may have: a conjunction of restrictions
 */
public interface AbstractQuery {
    /**
     * Add a query root corresponding to the given entity, forming a cartesian
     * product with any existing roots.
     * 
     * @param entity
     *            metamodel entity representing the entity of type X
     * @return query root corresponding to the given entity
     */
    <X> Root<X> from(Entity<X> entity);

    /**
     * Add a query root corresponding to the given entity, forming a cartesian
     * product with any existing roots.
     * 
     * @param entityClass
     *            the entity class
     * @return query root corresponding to the given entity
     */
    <X> Root<X> from(Class<X> entityClass);

    /**
     * Return the query roots.
     * 
     * @return the set of query roots
     */
    Set<Root<?>> getRoots();

    /**
     * Modify the query to restrict the query results according to the specified
     * boolean expression. Replaces the previously added restriction(s), if any.
     * 
     * @param restriction
     *            a simple or compound boolean expression
     * @return the modified query
     */
    AbstractQuery where(Expression<Boolean> restriction);

    /**
     * Modify the query to restrict the query results according to the
     * conjunction of the specified restriction predicates. Replaces the
     * previously added restriction(s), if any. If no restrictions are
     * specified, any previously added restrictions are simply removed.
     * 
     * @param restrictions
     *            zero or more restriction predicates
     * @return the modified query
     */
    AbstractQuery where(Predicate... restrictions);

    /**
     * Specify the expressions that are used to form groups over the query
     * results. Replaces the previous specified grouping expressions, if any. If
     * no grouping expressions are specified, any previously added grouping
     * expressions are simply removed.
     * 
     * @param grouping
     *            zero or more grouping expressions
     * @return the modified query
     */
    AbstractQuery groupBy(Expression<?>... grouping);

    /**
     * Specify a restriction over the groups of the query. Replaces the previous
     * having restriction(s), if any.
     * 
     * @param restriction
     *            a simple or compound boolean expression
     * @return the modified query
     */
    AbstractQuery having(Expression<Boolean> restriction);

    /**
     * Specify restrictions over the groups of the query according the
     * conjunction of the specified restriction predicates. Replaces the
     * previously added restriction(s), if any. If no restrictions are
     * specified, any previously added restrictions are simply removed.
     * 
     * @param restrictions
     *            zero or more restriction predicates
     * @return the modified query
     */
    AbstractQuery having(Predicate... restrictions);

    /**
     * Specify whether duplicate query results will be eliminated. A true value
     * will cause duplicates to be eliminated. A false value will cause
     * duplicates to be retained. If distinct has not been specified, duplicate
     * results must be retained.
     * 
     * @param distinct
     *            boolean value specifying whether duplicate results must be
     *            eliminated from the query result or whether they must be
     *            retained
     * @return the modified query.
     */
    AbstractQuery distinct(boolean distinct);

    /**
     * Return a list of the grouping expressions
     * 
     * @return the list of grouping expressions
     */
    List<Expression<?>> getGroupList();

    /**
     * Return the predicate that corresponds to the where clause restriction(s).
     * 
     * @return where clause predicate
     */
    Predicate getRestriction();

    /**
     * Return the predicate that corresponds to the restriction(s) over the
     * grouping items.
     * 
     * @return having clause predicate
     */
    Predicate getGroupRestriction();

    /**
     * Return whether duplicate query results must be eliminated or retained.
     * 
     * @return boolean indicating whether duplicate query results must be
     *         eliminated
     */
    boolean isDistinct();

    /**
     * Specify that the query is to be used as a subquery having the specified
     * return type.
     * 
     * @return subquery corresponding to the query
     */
    <U> Subquery<U> subquery(Class<U> type);
}