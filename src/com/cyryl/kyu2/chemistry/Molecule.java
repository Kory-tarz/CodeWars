package com.cyryl.kyu2.chemistry;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Molecule {

    private final static Comparator<String> elementComparator = Comparator
            .comparing((String element) -> element.equals("C") ? 0 : 1)
            .thenComparing((String element) -> element.equals("H") ? 0 : 1)
            .thenComparing((String element) -> element.equals("O") ? 0 : 1)
            .thenComparing(Comparator.naturalOrder());

    private String name;
    private int sequenceId = 1;
    private int atomId = 1;
    private final Map<Integer, List<Atom>> branchMap = new HashMap<>();
    private boolean locked = false;

    Molecule() {

    }

    Molecule(String name) {
        this.name = name;
    }

    /*
    Can take any number of arguments (positive integers).
    Adds new "branches" to the current molecule.
    Each argument gives the number of carbons of the new branch.
     */
    public Molecule brancher(int... branches) {
        if (locked) {
            throw new LockedMolecule();
        }
        Map<Integer, List<Atom>> newBranches = Arrays.stream(branches)
                .mapToObj((branchSize) ->
                        IntStream.range(0, branchSize).mapToObj((nr) -> Atom.createCarbon(getNextAtomId())).toList())
                .collect(Collectors.toMap((elements) -> getNextBranchId(), Function.identity()));
        branchMap.putAll(newBranches);
        return this;
    }

    private int getNextBranchId() {
        return sequenceId++;
    }

    private int getNextAtomId() {
        return atomId++;
    }

    /*
    Creates new bounds between two atoms of existing branches.
    Each argument is a tuple (python), array (ruby/JS), or T object (java) of four integers giving:
        c1 & b1: carbon and branch positions of the first atom
        c2 & b2: carbon and branch positions of the second atom
    All positions are 1-indexed, meaning (1,1,5,3) will bound the first carbon of the first branch with the fifth of the third branch.
    Only positive integers will be used.
     */
    public Molecule bounder(T... bounds) {
        if (locked) {
            throw new LockedMolecule();
        }
        for (T bound : bounds) {
            int carbonPos1 = bound.c1 - 1;
            int carbonPos2 = bound.c2 - 1;
            int branchPos1 = bound.b1;
            int branchPos2 = bound.b2;
            Atom firstAtom = branchMap.get(branchPos1).get(carbonPos1);
            Atom secondAtom = branchMap.get(branchPos2).get(carbonPos2);
            firstAtom.boundWith(secondAtom);
        }
        return this;
    }

    /*
    Mutates the carbon nc in the branch nb to the chemical element elt(given as a string).
    Don't forget that carbons and branches are 1-indexed.
    This is mutation: the id number of the Atom instance stays the same. See the Atom class specs about that.
     */
    public Molecule mutate(T... carbons) {
        if (locked) {
            throw new LockedMolecule();
        }
        for (T carbon : carbons) {
            int carbonPos = carbon.nc - 1;
            int branchPos = carbon.nb;
            String newElement = carbon.elt;
            Atom atom = branchMap.get(branchPos).get(carbonPos);
            atom.mutate(newElement);
        }
        return this;
    }

    /*
    Adds a new Atom of kind elt (string) on the carbon nc in the branch nb.
    Atoms added this way are not considered as being part of the branch they are bounded to and aren't considered a new branch of the molecule.
     */
    public Molecule add(T... elements) {
        if (locked) {
            throw new LockedMolecule();
        }
        for (T element : elements) {
            int carbonPos = element.nc - 1;
            int branchPos = element.nb;
            String newElement = element.elt;
            Atom atom = branchMap.get(branchPos).get(carbonPos);
            atom.boundWith(new Atom(newElement, getNextAtomId()));
        }
        return this;
    }

    /*
    Adds on the carbon nc in the branch nb a chain with all the provided elements, in the specified order. Meaning: m.add_chaining(2, 5, "N", "C", "C", "Mg", "Br") will add the chain ...-N-C-C-Mg-Br to the atom number 2 in the branch 5.
    As for the add method, this chain is not considered a new branch of the molecule.

    Special case with add_chaining: if an error occurs at any point when adding the chain, all its atoms have to be removed from the instance (even the valid ones).
     */
    public Molecule addChaining(int carbonPos, int branchPos, String... elements) {
        if (locked) {
            throw new LockedMolecule();
        }
        if (elements.length == 0) {
            return this;
        }
        int idBeforeChaining = atomId;
        try {
            Atom firstAtomInChain = null;
            Atom prevAtom = null;
            for (String element : elements) {
                Atom currAtom = new Atom(element, getNextAtomId());
                if (prevAtom != null) {
                    prevAtom.boundWith(currAtom);
                } else {
                    firstAtomInChain = currAtom;
                }
                prevAtom = currAtom;
            }
            Atom atom = branchMap.get(branchPos).get(carbonPos);
            atom.boundWith(firstAtomInChain);
        } catch (RuntimeException ex) {
            atomId = idBeforeChaining;
        }
        return this;
    }

    /*
    Finalizes the molecule instance, adding missing hydrogens everywhere and locking the object.
     */
    public Molecule closer() {
        if (locked) {
            throw new LockedMolecule();
        }
        visitAllAtoms(atom -> atom.addMissingHydrogens(this::getNextAtomId));
        return this;
    }

    private void visitAllAtoms(Consumer<Atom> atomConsumer) {
        Set<Atom> visitedAtoms = new HashSet<>();
    }

    private void visitBranch(Set<Atom> visited, Atom currAtom, Consumer<Atom> atomConsumer) {
        if (visited.contains(currAtom)) {
            return;
        }
        visited.add(currAtom);
        atomConsumer.accept(currAtom);
        currAtom.getBoundAtoms().forEach(atom -> visitBranch(visited, atom, atomConsumer));
    }

    /*
    Makes the molecule mutable again.
    Hydrogens should be removed, as well as any empty branch you might encounter during the process.
    After the molecule has been "unlocked", if by any (bad...) luck it does not have any branch left, throw an EmptyMolecule exception.
    The id numbers of the remaining atoms have to be continuous again (beginning at 1), keeping the order they had when the molecule was locked.
    After removing hydrogens, if you end up with some atoms that aren't connected in any way to the branches of the unlocked molecule,
    keep them anyway in the Molecule instance (for the sake of simplicity...).
    Once unlocked, the molecule has to be modifiable again, in any manner.
     */
    public Molecule unlock() {
        if (!locked) {
            throw new UnlockedMolecule();
        }
        locked = false;
        return this;
    }


    //To get the raw formula of the final molecule as a string (ex: "C4H10", "C5H10O2BrClS"
    public String getFormula() {
        if (!locked) {
            throw new UnlockedMolecule();
        }
        Map<String, Integer> count = new HashMap<>();
        Set<Atom> visitedAtoms = new HashSet<>();
        visitAllAtoms(atom -> count.put(atom.element, count.getOrDefault(atom.element, 0) + 1));
        branchMap.values().stream().flatMap(Collection::stream).forEach(atom -> countElements(count, visitedAtoms, atom));
        StringBuilder formula = new StringBuilder();
        count.keySet().stream().sorted(elementComparator).forEach(element -> {
            formula.append(element);
            int elementCount = count.get(element);
            if (elementCount > 1) {
                formula.append(elementCount);
            }
        });
        return formula.toString();
    }

    private void countElements(Map<String, Integer> count, Set<Atom> visited, Atom currAtom) {
        if (visited.contains(currAtom)) {
            return;
        }
        visited.add(currAtom);
        count.put(currAtom.element, count.getOrDefault(currAtom.element, 0) + 1);
        currAtom.getBoundAtoms().forEach(atom -> countElements(count, visited, atom));
    }

    //To get the value of the molecular weight of the final molecule in g/mol, as a double valu
    public double getMolecularWeight() {
        if (!locked) {
            throw new UnlockedMolecule();
        }
        Set<Atom> visitedAtoms = new HashSet<>();
        return branchMap.values().stream().flatMap(Collection::stream)
                .mapToDouble(atom -> calculateMolecularWeight(atom, visitedAtoms))
                .sum();
    }

    private double calculateMolecularWeight(Atom currAtom, Set<Atom> visited) {
        if (visited.contains(currAtom)) {
            return 0d;
        }
        visited.add(currAtom);
        double molecularWeight = Atom.ATOMIC_WEIGHT.get(currAtom.element);
        molecularWeight += currAtom.getBoundAtoms().stream()
                .mapToDouble(atom -> calculateMolecularWeight(atom, visited))
                .sum();
        return molecularWeight;
    }

    //To get a list of Atom objects. Atoms are appended to the list in the order of their creation:
    public List<Atom> getAtoms() {
        if (!locked) {
            throw new UnlockedMolecule();
        }
        Set<Atom> visitedAtoms = new HashSet<>();
        return branchMap.values().stream()
                .flatMap(Collection::stream)
                .flatMap(atom -> getAllBoundAtoms(atom, visitedAtoms).stream())
                .sorted(Comparator.comparingInt(atom -> atom.id)).toList();
    }

    private List<Atom> getAllBoundAtoms(Atom currAtom, Set<Atom> visited) {
        List<Atom> result = new ArrayList<>();
        if (visited.contains(currAtom)) {
            return result;
        }
        visited.add(currAtom);
        result.add(currAtom);
        result.addAll(currAtom.getBoundAtoms().stream().flatMap(atom -> getAllBoundAtoms(currAtom, visited).stream()).toList());
        return result;
    }

    //To get the name of the molecule, as a string of course, if given in the constructor
    public String getName() {
        return name;
    }

}

/*
An InvalidBond exception should be thrown each time you encounter a case where an atom exceeds its valence number or is bounded to itself
 */
class InvalidBond extends RuntimeException {
}

/*
After the molecule has been "unlocked", if by any (bad...) luck it does not have any branch left, throw an EmptyMolecule exception.
 */
class EmptyMolecule extends RuntimeException {

}

/*
The fields formula and molecular_weight or the associated getters (depending on your language) should throw an UnlockedMolecule exception
 if an user tries to access them while the molecule isn't locked (because we do not want the user to catch incomplete/invalid information).
 */
class UnlockedMolecule extends RuntimeException {

}

/*
In a similar manner, attempts of modification of a molecule after it has been locked
 should throw a LockedMolecule exception (the closer method follows this behavior too).
 */
class LockedMolecule extends RuntimeException {

}
