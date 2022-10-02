package edu.ust.cisc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class CiscArrayListTest extends CiscTest {
    private CiscArrayList<Integer> ciscArrayList;
    private Field elementData;
    private Field size;

    @BeforeEach
    void setUp() throws Exception {
        ciscArrayList = new CiscArrayList<Integer>();
        elementData = CiscArrayList.class.getDeclaredField("elementData");
        elementData.setAccessible(true);
        size = CiscArrayList.class.getDeclaredField("size");
        size.setAccessible(true);
    }

    @Test
    public void testFields() {
        try {
            assertEquals(3, CiscArrayList.class.getDeclaredFields().length, "CiscArrayList should only have \"DEFAULT_CAPACITY\", \"elementData\" and \"size\" fields");
        } catch(Exception e) {
            e.printStackTrace();
            fail("Exception thrown when attempting to access CiscArrayList fields");
        }
    }

    @Test
    public void testDefaultConstructor() {
        String methodCall = "CiscArrayList()";
        try {
            assertEquals(10, ((Object[])elementData.get(ciscArrayList)).length, generateErrorMessage(methodCall, "elementData capacity"));
            assertEquals(0, size.get(ciscArrayList), generateErrorMessage(methodCall, "size"));
        } catch (Exception e) {
            handleGenericException(methodCall, e);
        }
    }

    @Test
    public void testConstructorWithCapacity() {
        String methodCall = "CiscArrayList(20)";
        try {
            CiscArrayList<Integer> otherCiscArrayList = new CiscArrayList<Integer>(20);
            assertEquals(20, ((Object[])elementData.get(otherCiscArrayList)).length, generateErrorMessage(methodCall, "elementData capacity"));
            assertEquals(0, size.get(otherCiscArrayList), generateErrorMessage(methodCall, "size"));

            //try negative index
            methodCall = "CiscArrayList(-20)";
            assertThrows(IllegalArgumentException.class, () -> {
                new CiscArrayList<>(-20);
            }, generateErrorMessage(methodCall, "thrown exception"));
        } catch (Exception e) {
            handleGenericException(methodCall, e);
        }
    }

    @Test
    public void testIterator() {
        String methodCall = "iterator()";
        try {
            assertThrows(UnsupportedOperationException.class, () -> {
                ciscArrayList.iterator();
            }, generateErrorMessage(methodCall));
        } catch (Exception e) {
            handleGenericException(methodCall, e);
        }
    }

    @Test
    public void testSize() {
        String methodCall = "size()";
        try {
            Random r = new Random();
            int s = r.nextInt(100);
            size.set(ciscArrayList, s);
            CiscArrayList<Integer> calBeforeMethodCall = duplicateCiscArrayList();
            assertEquals(s, ciscArrayList.size(), generateErrorMessage(methodCall, "size", calBeforeMethodCall));
        } catch (Exception e) {
            handleGenericException(methodCall, e);
        }
    }

    @Test
    public void testAdd() {
        String methodCall = "add(E)";
        try {
            int numInts = 10;
            ArrayList<Integer> randomInts = getRandomIntArrayList(numInts, false);
            int randomInt;
            boolean r;
            CiscArrayList<Integer> calBeforeMethodCall;
            for(int i=0; i<numInts; i++) {
                randomInt = randomInts.get(i);
                methodCall = "add("+randomInt+")";
                calBeforeMethodCall = duplicateCiscArrayList();
                r = ciscArrayList.add(randomInt);
                assertEquals(true, r, generateErrorMessage(methodCall, "return value", calBeforeMethodCall));
                assertEquals(randomInts.get(i), ((Object[])elementData.get(ciscArrayList))[i], generateErrorMessage(methodCall, "elementData["+i+"]", calBeforeMethodCall));
                assertEquals(i+1, size.get(ciscArrayList), generateErrorMessage(methodCall, "size", calBeforeMethodCall));
            }
        } catch (Exception e) {
            handleGenericException(methodCall, e);
        }
    }

    @Test
    public void testRemove() {
        String methodCall = "remove(E)";
        try {
            ArrayList<Integer> randomInts;
            Object[] ciscElementData;
            Random r;
            int randInt;
            CiscArrayList<Integer> calBeforeMethodCall;
            for(int j=0; j<20; j++) {
                ciscArrayList = new CiscArrayList<>();
                int numInts = 10;
                randomInts = getRandomIntArrayList(numInts, false);
                ciscElementData = (Object[]) elementData.get(ciscArrayList);

                //setup ciscArrayList
                for (int i = 0; i < numInts; i++) {
                    ciscElementData[i] = randomInts.get(i);
                }
                size.set(ciscArrayList, numInts);
                r = new Random();
                randInt = r.nextInt(100) + 100;
                methodCall = "remove("+randInt+")";
                calBeforeMethodCall = duplicateCiscArrayList();
                //test non-existent element
                assertEquals(false, ciscArrayList.remove((Object) randInt), generateErrorMessage(methodCall, "return value", calBeforeMethodCall));
                //repeatedly remove random element from ciscArrayList
                for (int i = 0; i < numInts; i++) {
                    randInt = randomInts.get(r.nextInt(randomInts.size()));
                    methodCall = "remove("+randInt+")";
                    calBeforeMethodCall = duplicateCiscArrayList();
                    randomInts.remove((Object) randInt);
                    assertEquals(true, ciscArrayList.remove((Object) randInt), generateErrorMessage(methodCall, "return value", calBeforeMethodCall));
                    assertEquals(randomInts.size(), size.get(ciscArrayList), generateErrorMessage(methodCall, "size", calBeforeMethodCall));
                    assertArrayEquals(randomInts.toArray(), Arrays.copyOf(ciscElementData, randomInts.size()), generateErrorMessage(methodCall, "elementData", calBeforeMethodCall));
                    assertNull(((Object[])elementData.get(ciscArrayList))[(Integer)size.get(ciscArrayList)], generateErrorMessage(methodCall, "elementData[size]", calBeforeMethodCall));
                }
            }

        } catch (Exception e) {
            handleGenericException(methodCall, e);
        }
    }

    @Test
    public void testContains() {
        String methodCall = "contains(E)";
        try {
            Random r = new Random();
            CiscArrayList<Integer> calBeforeMethodCall;

            int randInt = r.nextInt(100);
            methodCall = "contains("+randInt+")";
            calBeforeMethodCall = duplicateCiscArrayList();

            //test with empty list
            assertEquals(false, ciscArrayList.contains(randInt), generateErrorMessage(methodCall, "return value", calBeforeMethodCall));
            int numInts = 10;
            ArrayList<Integer> randomInts = getRandomIntArrayList(numInts, true);
            for(int i=0; i<numInts; i++) {
                randInt = randomInts.get(i);
                methodCall = "contains("+randInt+")";
                calBeforeMethodCall = duplicateCiscArrayList();
                assertEquals(false, ciscArrayList.contains(randInt), generateErrorMessage(methodCall, "return value", calBeforeMethodCall));
                ((Object[])elementData.get(ciscArrayList))[i] = randomInts.get(i);
                size.set(ciscArrayList, i+1);
                for(int j=0; j<=i; j++) {
                    randInt = randomInts.get(j);
                    methodCall = "contains("+randInt+")";
                    calBeforeMethodCall = duplicateCiscArrayList();
                    assertEquals(true, ciscArrayList.contains(randInt), generateErrorMessage(methodCall, "return value", calBeforeMethodCall));
                }
            }
        } catch (Exception e) {
            handleGenericException(methodCall, e);

        }
    }

    @Test
    public void testIsEmpty() {
        String methodCall = "isEmpty()";
        try {
            Random r = new Random();
            CiscArrayList<Integer> calBeforeMethodCall = duplicateCiscArrayList();
            assertEquals(true, ciscArrayList.isEmpty(), generateErrorMessage(methodCall, "return value", calBeforeMethodCall));
            ((Object[])elementData.get(ciscArrayList))[0] = r.nextInt(100);
            size.set(ciscArrayList, 1);
            calBeforeMethodCall = duplicateCiscArrayList();
            assertEquals(false, ciscArrayList.isEmpty(), generateErrorMessage(methodCall, "return value", calBeforeMethodCall));
        } catch (Exception e) {
            handleGenericException(methodCall, e);
        }
    }

    @Test
    public void testClear() {
        String methodCall = "clear()";
        try {
            CiscArrayList<Integer> calBeforeMethodCall = duplicateCiscArrayList();
            ciscArrayList.clear();
            Object[] ciscElementData = ((Object[])elementData.get(ciscArrayList));
            assertEquals(0, size.get(ciscArrayList), generateErrorMessage(methodCall, "size", calBeforeMethodCall));

            Random r = new Random();
            int numInts = 10;
            for(int i=0; i<numInts; i++) {
                ciscElementData[i] = r.nextInt(100);
            }
            size.set(ciscArrayList, numInts);
            calBeforeMethodCall = duplicateCiscArrayList();
            ciscArrayList.clear();
            for(int i=0; i<numInts; i++) {
                assertNull(ciscElementData[i], generateErrorMessage(methodCall, "elementData["+i+"]", calBeforeMethodCall));
            }
            assertEquals(0, size.get(ciscArrayList), generateErrorMessage(methodCall, "size", calBeforeMethodCall));
        } catch (Exception e) {
            handleGenericException(methodCall, e);
        }
    }

    @Test
    public void testToArray() {
        String methodCall = "toArray()";
        try {
            CiscArrayList<Integer> calBeforeMethodCall = duplicateCiscArrayList();
            assertNotNull(ciscArrayList.toArray(), generateErrorMessage(methodCall, "non-null return value", calBeforeMethodCall));
            assertEquals(0, ciscArrayList.toArray().length, generateErrorMessage(methodCall, "return value capacity", calBeforeMethodCall));
            int numInts = 7;
            Object[] ciscElementData = ((Object[])elementData.get(ciscArrayList));
            ArrayList<Integer> randomInts = getRandomIntArrayList(numInts, false);
            for(int i=0; i<numInts; i++) {
                ciscElementData[i] = randomInts.get(i);
            }
            size.set(ciscArrayList, numInts);
            calBeforeMethodCall = duplicateCiscArrayList();
            assertArrayEquals(randomInts.toArray(), ciscArrayList.toArray(), generateErrorMessage(methodCall, "return value", calBeforeMethodCall));
        } catch (Exception e) {
            handleGenericException(methodCall, e);
        }
    }

    @Test
    public void testInsert() {
        String methodCall = "add(int, E)";
        try {
            int numInts = 10;
            ArrayList<Integer> checkList = new ArrayList();
            Object[] ciscElementData = (Object[])elementData.get(ciscArrayList);
            Random r = new Random();
            int randIndex;
            Integer randVal;
            CiscArrayList<Integer> calBeforeMethodCall;

            for(int i=0; i<numInts; i++) {
                randIndex = r.nextInt(checkList.size()+1);
                randVal = r.nextInt(100);
                methodCall = "add("+randIndex+","+randVal+")";
                calBeforeMethodCall = duplicateCiscArrayList();
                checkList.add(randIndex, randVal);
                ciscArrayList.add(randIndex, randVal);
                assertArrayEquals(Arrays.copyOf(checkList.toArray(), ciscElementData.length), ciscElementData, generateErrorMessage(methodCall, "elementData", calBeforeMethodCall));
                assertEquals(checkList.size(), size.get(ciscArrayList), generateErrorMessage(methodCall, "size", calBeforeMethodCall));
            }
            //try negative index
            final int randValue = r.nextInt(100);
            methodCall = "add(-1,"+randValue+")";
            calBeforeMethodCall = duplicateCiscArrayList();
            assertThrows(IndexOutOfBoundsException.class, () -> {
                ciscArrayList.add(-1, randValue);
            }, generateErrorMessage(methodCall, "thrown exception", calBeforeMethodCall));
            //try index too large
            methodCall = "add("+(numInts+1)+","+randValue+")";
            calBeforeMethodCall = duplicateCiscArrayList();
            assertThrows(IndexOutOfBoundsException.class, () -> {
                ciscArrayList.add(numInts+1, randValue);
            }, generateErrorMessage(methodCall, "thrown exception", calBeforeMethodCall));
        } catch (Exception e) {
            handleGenericException(methodCall, e);
        }
    }

    @Test
    public void testGet() {
        String methodCall = "get(int)";
        try {
            Object[] ciscElementData = (Object[])elementData.get(ciscArrayList);
            Random r = new Random();
            int numInts = 10;
            int randInt;
            CiscArrayList<Integer> calBeforeMethodCall;

            for(int i=0; i<numInts; i++) {
                ciscElementData[i] = r.nextInt(100);
            }
            size.set(ciscArrayList, numInts);

            for(int i=0; i<numInts; i++) {
                calBeforeMethodCall = duplicateCiscArrayList();
                randInt = ciscArrayList.get(i);
                methodCall = "get("+randInt+")";
                assertEquals(ciscElementData[i], randInt, generateErrorMessage(methodCall, "return value", calBeforeMethodCall));
            }
            //try negative index
            methodCall = "get(-1)";
            calBeforeMethodCall = duplicateCiscArrayList();
            assertThrows(IndexOutOfBoundsException.class, () -> {
                ciscArrayList.get(-1);
            }, generateErrorMessage(methodCall, "thrown exception", calBeforeMethodCall));

            //try index too large
            methodCall = "get("+numInts+")";
            calBeforeMethodCall = duplicateCiscArrayList();
            assertThrows(IndexOutOfBoundsException.class, () -> {
                ciscArrayList.get(numInts);
            }, generateErrorMessage(methodCall, "thrown exception", calBeforeMethodCall));
        } catch (Exception e) {
            handleGenericException(methodCall, e);
        }
    }

    @Test
    public void testIndexOf() {
        String methodCall = "indexOf(E)";
        try {
            Random r = new Random();
            //test with empty list
            int randInt = r.nextInt(100);
            methodCall = "indexOf("+randInt+")";
            CiscArrayList<Integer> calBeforeMethodCall = duplicateCiscArrayList();
            assertEquals(-1, ciscArrayList.indexOf(randInt), generateErrorMessage(methodCall, "return value", calBeforeMethodCall));
            int numInts = 10;
            ArrayList<Integer> randomInts = getRandomIntArrayList(numInts, true);
            for(int i=0; i<numInts; i++) {
                randInt = randomInts.get(i);
                methodCall = "indexOf("+randInt+")";
                calBeforeMethodCall = duplicateCiscArrayList();
                assertEquals(-1, ciscArrayList.indexOf(randInt), generateErrorMessage(methodCall, "return value", calBeforeMethodCall));
                ((Object[])elementData.get(ciscArrayList))[i] = randomInts.get(i);
                size.set(ciscArrayList, i+1);
                for(int j=0; j<=i; j++) {
                    randInt = randomInts.get(j);
                    methodCall = "indexOf("+randInt+")";
                    calBeforeMethodCall = duplicateCiscArrayList();
                    assertEquals(randomInts.indexOf(randomInts.get(j)), ciscArrayList.indexOf(randInt), generateErrorMessage(methodCall, "return value", calBeforeMethodCall));
                }
            }
        } catch (Exception e) {
            handleGenericException(methodCall, e);
        }
    }

    @Test
    public void testRemoveAtIndex() {
        String methodCall = "remove(int)";
        try {
            int numInts = 10;
            ArrayList<Integer> randomInts = getRandomIntArrayList(numInts, false);
            Object[] ciscElementData = (Object[])elementData.get(ciscArrayList);
            CiscArrayList<Integer> calBeforeMethodCall;

            //setup ciscArrayList
            for(int i=0; i<numInts; i++) {
                ciscElementData[i] = randomInts.get(i);
            }
            size.set(ciscArrayList, numInts);
            Random r = new Random();
            int randomIndex;

            //repeatedly remove random element from ciscArrayList
            for(int i=0; i<numInts; i++) {
                randomIndex = r.nextInt(randomInts.size());
                methodCall = "remove("+randomIndex+")";
                calBeforeMethodCall = duplicateCiscArrayList();
                assertEquals(randomInts.remove(randomIndex), ciscArrayList.remove(randomIndex), generateErrorMessage(methodCall, "return value", calBeforeMethodCall));
                assertEquals(randomInts.size(), size.get(ciscArrayList), generateErrorMessage(methodCall, "size", calBeforeMethodCall));
                assertArrayEquals(randomInts.toArray(), Arrays.copyOf(ciscElementData, randomInts.size()), generateErrorMessage(methodCall, "elementData", calBeforeMethodCall));
                assertNull(((Object[])elementData.get(ciscArrayList))[(Integer)size.get(ciscArrayList)], generateErrorMessage(methodCall, "elementData[size]", calBeforeMethodCall));
            }
            //try negative index
            methodCall = "remove(-1)";
            calBeforeMethodCall = duplicateCiscArrayList();
            assertThrows(IndexOutOfBoundsException.class, () -> {
                ciscArrayList.remove(-1);
            }, generateErrorMessage(methodCall, "thrown exception", calBeforeMethodCall));

            //try index too large
            methodCall = "remove(0)";
            calBeforeMethodCall = duplicateCiscArrayList();
            assertThrows(IndexOutOfBoundsException.class, () -> {
                ciscArrayList.remove(0);
            }, generateErrorMessage(methodCall, "thrown exception", calBeforeMethodCall));
        } catch (Exception e) {
            handleGenericException(methodCall, e);
        }
    }

    @Test
    public void testSet() {
        String methodCall = "set(int, E)";
        try {
            int numInts = 10;
            ArrayList<Integer> checkList = new ArrayList();
            Object[] ciscElementData = (Object[])elementData.get(ciscArrayList);
            Random r = new Random();
            int randIndex;
            Integer randVal;
            CiscArrayList<Integer> calBeforeMethodCall;

            for(int i=0; i<numInts; i++) {
                randVal = r.nextInt(100);
                checkList.add(randVal);
                ciscElementData[i] = randVal;
                size.set(ciscArrayList, i+1);
                randIndex = r.nextInt(checkList.size());
                randVal = r.nextInt(100);
                checkList.set(randIndex, randVal);
                methodCall = "set("+randIndex+","+randVal+")";
                calBeforeMethodCall = duplicateCiscArrayList();
                ciscArrayList.set(randIndex, randVal);
                for(int j=0; j<=i; j++) {
                    assertEquals(checkList.get(j), ciscElementData[j], generateErrorMessage(methodCall, "elementData["+j+"]", calBeforeMethodCall));
                }
                assertEquals(i+1, size.get(ciscArrayList), generateErrorMessage(methodCall, "size", calBeforeMethodCall));
            }

            //try negative index
            final int randomValue = r.nextInt(100);
            methodCall = "set(-1,"+randomValue+")";
            calBeforeMethodCall = duplicateCiscArrayList();
            assertThrows(IndexOutOfBoundsException.class, () -> {
                ciscArrayList.set(-1, randomValue);
            }, generateErrorMessage(methodCall, "thrown exception", calBeforeMethodCall));

            //try index too large
            methodCall = "set("+numInts+","+randomValue+")";
            calBeforeMethodCall = duplicateCiscArrayList();
            assertThrows(IndexOutOfBoundsException.class, () -> {
                ciscArrayList.set(numInts, randomValue);
            }, generateErrorMessage(methodCall, "thrown exception", calBeforeMethodCall));
        } catch (Exception e) {
            handleGenericException(methodCall, e);
        }
    }

    @Test
    public void testEnsureCapacity() {
        String methodCall = "ensureCapacity(int)";
        try {
            int numInts = 10;
            ArrayList<Integer> randomInts = getRandomIntArrayList(numInts, false);
            Object[] ciscElementData = (Object[])elementData.get(ciscArrayList);
            CiscArrayList<Integer> calBeforeMethodCall;

            for(int i=0; i<numInts; i++) {
                ciscElementData[i] = randomInts.get(i);
            }
            size.set(ciscArrayList, numInts);


            Random r = new Random();
            int randomInt = r.nextInt(100);
            randomInts.add(randomInt);

            //should result in ensureCapacity array resizing
            methodCall = "add("+randomInt+")";
            calBeforeMethodCall = duplicateCiscArrayList();
            ciscArrayList.add(randomInt);
            ciscElementData = (Object[])elementData.get(ciscArrayList);
            assertArrayEquals(Arrays.copyOf(ciscElementData, 21), ciscElementData, generateErrorMessage(methodCall, "elementData", calBeforeMethodCall));
            assertEquals(randomInts.size(), size.get(ciscArrayList), generateErrorMessage(methodCall, "size", calBeforeMethodCall));

            //shouldn't result in ensureCapacity array resizing
            methodCall = "ensureCapacity(21)";
            calBeforeMethodCall = duplicateCiscArrayList();
            ciscArrayList.ensureCapacity(21);
            assertArrayEquals(Arrays.copyOf(ciscElementData, 21), ciscElementData, generateErrorMessage(methodCall, "elementData", calBeforeMethodCall));
            assertEquals(randomInts.size(), size.get(ciscArrayList), generateErrorMessage(methodCall, "size", calBeforeMethodCall));

            //should result in ensureCapacity array resizing
            methodCall = "ensureCapacity(22)";
            calBeforeMethodCall = duplicateCiscArrayList();
            ciscArrayList.ensureCapacity(22);
            ciscElementData = (Object[])elementData.get(ciscArrayList);
            assertArrayEquals(Arrays.copyOf(ciscElementData, 43), ciscElementData, generateErrorMessage(methodCall, "elementData", calBeforeMethodCall));
            assertEquals(randomInts.size(), size.get(ciscArrayList), generateErrorMessage(methodCall, "size", calBeforeMethodCall));

            //should result in ensureCapacity array resizing
            methodCall = "ensureCapacity(90)";
            calBeforeMethodCall = duplicateCiscArrayList();
            ciscArrayList.ensureCapacity(90);
            ciscElementData = (Object[])elementData.get(ciscArrayList);
            assertArrayEquals(Arrays.copyOf(ciscElementData, 90), ciscElementData, generateErrorMessage(methodCall, "elementData", calBeforeMethodCall));
            assertEquals(randomInts.size(), size.get(ciscArrayList), generateErrorMessage(methodCall, "size", calBeforeMethodCall));
        } catch (Exception e) {
            handleGenericException(methodCall, e);
        }
    }

    private ArrayList<Integer> getRandomIntArrayList(int size, boolean allUnique) {
        ArrayList<Integer> randomInts = new ArrayList<Integer>();
        Random r = new Random();
        int randomInt;
        for(int i=0; i<size; i++) {
            do {
                randomInt = r.nextInt(100);
            } while(allUnique && randomInts.contains(randomInt));
            randomInts.add(randomInt);
        }
        return randomInts;
    }

    private CiscArrayList duplicateCiscArrayList() {
        CiscArrayList cal = new CiscArrayList();
        try {
            Object[] ed = (Object[])elementData.get(ciscArrayList);
            Object[] edCopy = new Object[ed.length];
            for(int i=0; i<edCopy.length; i++) {
                if(ed[i] != null) {
                    edCopy[i] = Integer.valueOf((Integer) ed[i]);
                }
            }
            elementData.set(cal, edCopy);
            size.set(cal, size.get(ciscArrayList));
        } catch(Exception e) {
            e.printStackTrace();
        }
        return cal;
    }

    @Override
    protected String generateIllustration(Object ciscDataStructure) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("\telementData: " + Arrays.toString((Object[]) elementData.get(ciscDataStructure)));
            sb.append("\n\tsize: " + size.get(ciscDataStructure));
        } catch(Exception e) {
            sb.append(e.getStackTrace());
        }
        return sb.toString();
    }

    @Override
    protected String generateIllustration() {
        return generateIllustration(ciscArrayList);
    }
}