package com.cyryl.kyu2.chemistry;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Atom {
    public static final Map<String, Integer> VALENCE_NUMBER = Stream.of(new Object[][]{
            {"H", 1},
            {"B", 3},
            {"C", 4},
            {"N", 3},
            {"O", 2},
            {"F", 1},
            {"Mg", 2},
            {"P", 3},
            {"S", 2},
            {"Cl", 1},
            {"Br", 1}
    }).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));

    public static final Map<String, Double> ATOMIC_WEIGHT = Stream.of(new Object[][]{
            {"H", 1.0},
            {"B", 10.8},
            {"C", 12.0},
            {"N", 14.0},
            {"O", 16.0},
            {"F", 19.0},
            {"Mg", 24.3},
            {"P", 31.0},
            {"S", 32.1},
            {"Cl", 35.5},
            {"Br", 80.0}
    }).collect(Collectors.toMap(data -> (String) data[0], data -> (Double) data[1]));

    private static final Comparator<Atom> atomComparator = Comparator
            .comparing((Atom atom) -> atom.element.equals("C") ? 0 : 1)
            .thenComparing((Atom atom) -> atom.element.equals("O") ? 0 : 1)
            .thenComparing((Atom atom) -> atom.element.equals("H") ? 0 : -1)
            .thenComparing(atom -> atom.element)
            .thenComparingInt(atom -> atom.id);

    public static Atom createHydrogen(int id) {
        return new Atom("H", id);
    }

    public static Atom createCarbon(int id) {
        return new Atom("C", id);
    }

    public int id;
    public String element;
    private int availableBonds;
    private List<Atom> bondedAtoms;

    public Atom(String elt, int id_) {
        element = elt;
        id = id_;
        availableBonds = VALENCE_NUMBER.get(element);
        bondedAtoms = new ArrayList<>();
    }

    public void mutate(String newElement) {
        int currBonds = VALENCE_NUMBER.get(element) - availableBonds;
        int newMaxBonds = VALENCE_NUMBER.get(newElement);
        if (newMaxBonds < currBonds) {
            throw new InvalidBond();
        }
        this.element = newElement;
        this.availableBonds = newMaxBonds - currBonds;
    }

    public void bondWith(Atom other) {
        if (this.availableBonds == 0 || other.availableBonds == 0 || this.equals(other)) {
            throw new InvalidBond();
        }
        this.availableBonds--;
        bondedAtoms.add(other);
        other.availableBonds--;
        other.bondedAtoms.add(this);
    }

    public List<Atom> getBondedAtoms() {
        return bondedAtoms.stream().toList();
    }

    public void addMissingHydrogens(Supplier<Atom> atomSupplier) {
        while (availableBonds > 0) {
            bondWith(atomSupplier.get());
        }
    }

    public void removeAllHydrogenBonds() {
        bondedAtoms.stream().filter(Atom::isHydrogen).forEach(this::removeBond);
    }

    private void removeBond(Atom bondedAtom) {
        bondedAtoms = this.getBondedAtoms().stream().filter(Predicate.not(bondedAtom::equals)).collect(Collectors.toList());
        this.availableBonds++;
        bondedAtom.bondedAtoms = getBondedAtoms().stream().filter(Predicate.not(this::equals)).collect(Collectors.toList());
        bondedAtom.availableBonds++;
    }

    @Override
    public int hashCode() {
        return id;
    }        //  Do not modify this method!!

    @Override
    public boolean equals(Object other) {       //  Do not modify this method!!
        if (other != null && other instanceof Atom) {
            Atom that = (Atom) other;
            return id == that.id;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Atom(").append(element).append(".").append(id);
        if (bondedAtoms.isEmpty()) {
            return sb.append(")").toString();
        }
        sb.append(": ");
        bondedAtoms.stream().sorted(atomComparator).forEach(atom -> sb.append(atom.toSubString()).append(","));
        sb.setLength(sb.length() - 1);
        return sb.append(")").toString();
    }

    public String toSubString() {
        StringBuilder sb = new StringBuilder(element);
        if (!this.isHydrogen()) {
            sb.append(id);
        }
        return sb.toString();
    }

    public boolean isHydrogen() {
        return element.equals("H");
    }
}


