package net.hdme.grouptools.mtfinder;

import net.hdme.grouptools.mtchecker.MultiplicationTable;

import java.util.List;

/**
 * Given a partially filled multiplication table,
 * a MTFinder searches for all tables representing a group.
 */
public class MTFinder {

    private List<MultiplicationTable> results;

    private void search(int[][] table) {}

    /**
     * Given a partially filled multiplication table,
     * find all possible tables representing groups.
     * The table contains N rows and columns representing elements 1~N,
     * while the identity element 0 and multiplication with it is implicitly defined.
     * @param template a partially filled table, where negative values indicate blanks and the identity element row/column is omitted
     * @return all possible tables representing groups
     */
    public FinderResult findGroups(int[][] template) {
        // TODO: Impl
        return null;
    }

}
