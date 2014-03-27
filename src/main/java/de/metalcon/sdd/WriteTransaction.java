package de.metalcon.sdd;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import de.metalcon.sdd.exception.EmptyIdException;
import de.metalcon.sdd.exception.InvalidNodeTypeException;
import de.metalcon.sdd.exception.InvalidPropertyException;
import de.metalcon.sdd.exception.InvalidRelationException;

public class WriteTransaction {

    private Sdd sdd;

    private boolean commited = false;

    private Queue<Action> actions = new LinkedList<Action>();

    /* package */WriteTransaction(
            Sdd sdd) {
        this.sdd = sdd;
    }

    public boolean commit() {
        if (commited) {
            throw new IllegalStateException("Arealdy commited.");
        }

        if (getActions().isEmpty()) {
            throw new IllegalStateException("Empty transaction.");
        }
        boolean result = sdd.commit(this);
        if (result) {
            commited = true;
        }
        return result;
    }

    public void setProperties(
            long nodeId,
            String nodeType,
            Map<String, String> properties) throws InvalidNodeTypeException,
            InvalidPropertyException, EmptyIdException {
        if (commited) {
            throw new IllegalStateException("Arealdy commited.");
        }

        actions.add(new SetPropertiesAction(sdd, nodeId, nodeType, properties));
    }

    /**
     * @param toId
     *            If this is <code>0L</code>, it deletes the relation.
     */
    public void setRelation(
            long nodeId,
            String nodeType,
            String relation,
            long toId) throws InvalidNodeTypeException,
            InvalidRelationException, EmptyIdException {
        if (commited) {
            throw new IllegalStateException("Arealdy commited.");
        }

        actions.add(new SetRelationAction(sdd, nodeId, nodeType, relation, toId));
    }

    public void setRelations(
            long nodeId,
            String nodeType,
            String relation,
            long[] toIds) throws InvalidNodeTypeException,
            InvalidRelationException, EmptyIdException {
        if (commited) {
            throw new IllegalStateException("Arealdy commited.");
        }

        actions.add(new SetRelationsAction(sdd, nodeId, nodeType, relation,
                toIds));
    }

    public void addRelations(
            long nodeId,
            String nodeType,
            String relation,
            long[] toIds) throws InvalidRelationException,
            InvalidNodeTypeException, EmptyIdException {
        if (commited) {
            throw new IllegalStateException("Arealdy commited.");
        }

        actions.add(new AddRelationsAction(sdd, nodeId, nodeType, relation,
                toIds));
    }

    public void delete(long nodeId) throws EmptyIdException {
        if (commited) {
            throw new IllegalStateException("Arealdy commited.");
        }

        actions.add(new DeleteAction(sdd, nodeId));
    }

    public void deleteRelations(
            long nodeId,
            String nodeType,
            String relation,
            long[] toIds) throws InvalidRelationException,
            InvalidNodeTypeException, EmptyIdException {
        if (commited) {
            throw new IllegalStateException("Arealdy commited.");
        }

        actions.add(new DeleteRelationsAction(sdd, nodeId, nodeType, relation,
                toIds));
    }

    /* package */Queue<Action> getActions() {
        return actions;
    }

}
