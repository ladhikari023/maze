package utility;

import java.util.*;
/*
 * Generic utility class designed for the Kruskal algorithm
 * Creates a list of disjoint sets for each object in a provided
 * list, and can join the sets of two specified objects together
 */
public class Partition<T> {
    List<Set<T>> setList;

    public Partition(Collection<T> objList) {
        setList = new ArrayList<>();
        for(T obj : objList) {
            Set<T> set = new HashSet<>();
            set.add(obj);
            setList.add(set);
        }
    }

    public void join(T objOne, T objTwo) {
        Set<T> setOne = new HashSet<>();
        Set<T> setTwo = new HashSet<>();

        for(Set<T> set : setList) {
            if(set.contains(objOne)) {
                setOne = set;
            }  else if(set.contains(objTwo)) {
                setTwo = set;
            }
        }
        Set<T> combinedSet = new HashSet<>();
        combinedSet.addAll(setOne);
        combinedSet.addAll(setTwo);
        setList.remove(setOne);
        setList.remove(setTwo);
        setList.add(combinedSet);
    }

    public boolean inSameSet(T objOne, T objTwo) {
        for(Set<T> set : setList) {
           if(set.contains(objOne) && set.contains(objTwo)) {
               return false;
           }
        }
        return true;
    }


}
