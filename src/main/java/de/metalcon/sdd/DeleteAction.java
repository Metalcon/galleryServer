package de.metalcon.sdd;

import java.util.Queue;

import de.metalcon.sdd.exception.EmptyIdException;

public class DeleteAction extends Action {

    private long nodeId;

    /* package */DeleteAction(
            Sdd sdd,
            long nodeId) {
        super(sdd);

        if (nodeId == Sdd.EMPTY_ID) {
            throw new EmptyIdException();
        }

        this.nodeId = nodeId;
    }

    @Override
    public void runAction(Queue<Action> actions) {
        sdd.actionDelete(actions, nodeId);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // we define that two distinct DeleteActions are never equal
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 47220;
        int mult = 421;

        hash = hash * mult + ((Long) nodeId).hashCode();

        return hash;
    }

}
