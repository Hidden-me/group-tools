package net.hdme.grouptools.mtfinder;

import net.hdme.grouptools.mtchecker.MultiplicationTable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class FinderResult {

    public final Set<MultiplicationTable> tables;

    public FinderResult(Collection<MultiplicationTable> tables) {
        this.tables = new HashSet<>(tables);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FinderResult result) {
            return tables.equals(result.tables);
        }
        return false;
    }

    @Override
    public String toString() {
        return tables.toString();
    }

}
