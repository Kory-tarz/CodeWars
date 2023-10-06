package com.cyryl.kyu2.chemistry;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
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
    private Map<Integer, List<Atom>> branchMap = new HashMap<>();
    private boolean locked = false;
    private List<Atom> allAtoms = new ArrayList<>();

    Molecule() {
        this.name = "";
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
        newBranches.values().forEach((atoms -> {
            allAtoms.addAll(atoms);
            for (int i = 1; i < atoms.size(); i++) {
                atoms.get(i).bondWith(atoms.get(i - 1));
            }
        }));
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
    Creates new bonds between two atoms of existing branches.
    Each argument is a tuple (python), array (ruby/JS), or T object (java) of four integers giving:
        c1 & b1: carbon and branch positions of the first atom
        c2 & b2: carbon and branch positions of the second atom
    All positions are 1-indexed, meaning (1,1,5,3) will bond the first carbon of the first branch with the fifth of the third branch.
    Only positive integers will be used.
     */
    public Molecule bounder(T... bonds) {
        if (locked) {
            throw new LockedMolecule();
        }
        for (T bond : bonds) {
            int carbonPos1 = bond.c1 - 1;
            int carbonPos2 = bond.c2 - 1;
            int branchPos1 = bond.b1;
            int branchPos2 = bond.b2;
            Atom firstAtom = branchMap.get(branchPos1).get(carbonPos1);
            Atom secondAtom = branchMap.get(branchPos2).get(carbonPos2);
            firstAtom.bondWith(secondAtom);
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
    Atoms added this way are not considered as being part of the branch they are bonded to and aren't considered a new branch of the molecule.
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
            Atom newAtom = new Atom(newElement, getNextAtomId());
            try {
                atom.bondWith(newAtom);
            } catch (RuntimeException ex) {
                this.atomId--;
                throw ex;
            }
            allAtoms.add(newAtom);
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
            List<Atom> createdAtoms = new ArrayList<>();
            for (String element : elements) {
                Atom currAtom = new Atom(element, getNextAtomId());
                if (prevAtom != null) {
                    prevAtom.bondWith(currAtom);
                } else {
                    firstAtomInChain = currAtom;
                }
                createdAtoms.add(currAtom);
                prevAtom = currAtom;
            }
            Atom atom = branchMap.get(branchPos).get(carbonPos - 1);
            atom.bondWith(firstAtomInChain);
            allAtoms.addAll(createdAtoms);
        } catch (RuntimeException ex) {
            atomId = idBeforeChaining;
            throw ex;
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
        List<Atom> hydrogens = new ArrayList<>();
        allAtoms.forEach(atom -> atom.addMissingHydrogens(() -> {
            Atom hydrogen = Atom.createHydrogen(getNextAtomId());
            hydrogens.add(hydrogen);
            return hydrogen;
        }));
        allAtoms.addAll(hydrogens);
        locked = true;
        return this;
    }

    private void visitAllAtoms(Consumer<Atom> atomConsumer) {
        Set<Atom> visitedAtoms = new HashSet<>();
        branchMap.values().stream().flatMap(Collection::stream).forEach(atom -> visitBranch(visitedAtoms, atom, atomConsumer));
    }

    private void visitBranch(Set<Atom> visited, Atom currAtom, Consumer<Atom> atomConsumer) {
        if (visited.contains(currAtom)) {
            return;
        }
        visited.add(currAtom);
        currAtom.getBondedAtoms().forEach(atom -> visitBranch(visited, atom, atomConsumer));
        atomConsumer.accept(currAtom);
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
        removeHydrogens();
        removeEmptyBranches();
        if (branchMap.isEmpty()) {
            throw new EmptyMolecule();
        }
        recalculateBranchIds();
        recalculateAtomIds();
        locked = false;
        return this;
    }

    private void removeHydrogens() {
        allAtoms.forEach(Atom::removeAllHydrogenBonds);
        allAtoms = allAtoms.stream().filter(Predicate.not(Atom::isHydrogen)).collect(Collectors.toList());
        branchMap.keySet()
                .forEach(key -> branchMap.put(key, branchMap.get(key).stream().filter(atom -> !atom.isHydrogen()).toList()));
    }

    private void removeEmptyBranches() {
        branchMap = branchMap.entrySet().stream()
                .filter(entry -> !entry.getValue().isEmpty()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private void recalculateBranchIds() {
        sequenceId = 1;
        Map<Integer, List<Atom>> newBranchMap = new HashMap<>();
        branchMap.keySet().stream().sorted().forEach(key -> newBranchMap.put(getNextBranchId(), branchMap.get(key)));
        branchMap = newBranchMap;
    }

    private void recalculateAtomIds() {
        this.atomId = 1;
        for (Atom atom : getAtoms()) {
            atom.id = getNextAtomId();
        }
    }


    //To get the raw formula of the final molecule as a string (ex: "C4H10", "C5H10O2BrClS"
    public String getFormula() {
        if (!locked) {
            throw new UnlockedMolecule();
        }
        Map<String, Integer> count = new HashMap<>();
        allAtoms.forEach(atom -> count.put(atom.element, count.getOrDefault(atom.element, 0) + 1));
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

    //To get the value of the molecular weight of the final molecule in g/mol, as a double value
    public double getMolecularWeight() {
        if (!locked) {
            throw new UnlockedMolecule();
        }
        var context = new Object() {
            Double molecularWeight = 0d;
        };
        allAtoms.forEach(atom -> context.molecularWeight += Atom.ATOMIC_WEIGHT.get(atom.element));
        return context.molecularWeight;
    }

    //To get a list of Atom objects. Atoms are appended to the list in the order of their creation:
    public List<Atom> getAtoms() {
        return allAtoms.stream().sorted(Comparator.comparingInt(atom -> atom.id)).toList();
    }

    //To get the name of the molecule, as a string of course, if given in the constructor
    public String getName() {
        return name;
    }

}

/*
An InvalidBond exception should be thrown each time you encounter a case where an atom exceeds its valence number or is bonded to itself
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
 if a user tries to access them while the molecule isn't locked (because we do not want the user to catch incomplete/invalid information).
 */
class UnlockedMolecule extends RuntimeException {

}

/*
In a similar manner, attempts of modification of a molecule after it has been locked
 should throw a LockedMolecule exception (the closer method follows this behavior too).
 */
class LockedMolecule extends RuntimeException {

}
