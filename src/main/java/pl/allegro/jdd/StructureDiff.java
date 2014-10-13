package pl.allegro.jdd;

import com.google.common.collect.MapDifference;
import com.google.common.collect.TreeTraverser;

import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Maps.difference;

public class StructureDiff extends TreeTraverser<Employee> {
    public Changes calculate(Employee oldCTO, Employee newCTO) {
        checkNotNull(oldCTO);
        checkNotNull(newCTO);
        MapDifference<Employee, Integer> diff = difference(getSalariesMap(oldCTO), getSalariesMap(newCTO));

        return new Changes(
                diff.entriesOnlyOnLeft().keySet(),
                diff.entriesOnlyOnRight().keySet(),
                diff.entriesDiffering().keySet()
        );
    }

    private Map<Employee, Integer> getSalariesMap(Employee boss) {
        return preOrderTraversal(boss).toMap(Employee::getSalary);
    }

    /*
    Note: if guava's TreeTraverser was a functional interface with default methods
    rather than an abstract class, it would be possible to construct it in a more
    simple way, asigning from a method reference that gets children of a node:

    TreeTraverser<Employee> traverser = Employee::getSubordinates;
     */
    @Override
    public Iterable<Employee> children(Employee node) {
        return node.getSubordinates();
    }
}
