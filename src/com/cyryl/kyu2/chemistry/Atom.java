package com.cyryl.kyu2.chemistry;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Atom implements Comparable<Atom> {
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

    public static Atom createHydrogen(int id) {
        return new Atom("H", id);
    }

    public static Atom createCarbon(int id) {
        return new Atom("C", id);
    }

    public int id;
    public String element;
    private int availableBounds;
    private TreeSet<Atom> boundAtoms;

    public Atom(String elt, int id_) {
        element = elt;
        id = id_;
        availableBounds = VALENCE_NUMBER.get(element);
        boundAtoms = new TreeSet<>();
    }

    public void mutate(String newElement) {
        int currentBounds = VALENCE_NUMBER.get(element) - availableBounds;
        int newMaxBounds =  VALENCE_NUMBER.get(newElement);
        if (newMaxBounds < currentBounds) {
            throw new InvalidBond();
        }
        this.element = newElement;
        this.availableBounds = newMaxBounds - currentBounds;
    }

    public void boundWith(Atom other) {
        if (this.availableBounds == 0 || other.availableBounds == 0 || this.equals(other)) {
            throw new InvalidBond();
        }
        this.availableBounds--;
        boundAtoms.add(other);
        other.availableBounds--;
        other.boundAtoms.add(this);
    }

    public List<Atom> getBoundAtoms() {
        return boundAtoms.stream().toList();
    }

    public void addMissingHydrogens(Supplier<Integer> idSupplier) {
        while (availableBounds > 0) {
            boundWith(Atom.createHydrogen(idSupplier.get()));
        }
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

    //"Atom(C.24: C1,O6,N2,H)";
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Atom(").append(element).append(".").append(id);
        if (boundAtoms.isEmpty()) {
            return sb.append(")").toString();
        }
        sb.append(": ");
        Iterator<Atom> atomIterator = boundAtoms.descendingIterator();
        atomIterator.forEachRemaining(atom -> sb.append(atom.toSubString()).append(","));
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

    @Override
    public int compareTo(Atom other) {
        if (this.element.equals(other.element)) {
            return Integer.compare(this.id, other.id);
        }
        if (this.isHydrogen()) {
            return 1;
        }
        if (other.isHydrogen()) {
            return -1;
        }
        return this.element.compareTo(other.element);
    }
}


