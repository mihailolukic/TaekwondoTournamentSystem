package app.backend.dataStorage;

import java.util.ArrayList;

/**
 * Created by Mihailo on 2/4/2017.
 */
public class CategoryFactory {

    public static final String MALE_YOUTH = "Male Youth";
    public static final String MALE_OLDER_YOUTH = "Male Older Youth";
    public static final String MALE_JUNIORS = "Male Juniors";
    public static final String MALE_OLDER_JUNIORS = "Male Older Juniors";
    public static final String MALE_SENIOR = "Male Seniors";
    public static final String MALE_VETERANS = "Male Veterans";

    public static final String FEMALE_YOUTH = "Female Youth";
    public static final String FEMALE_OLDER_YOUTH = "Female Older Youth";
    public static final String FEMALE_JUNIORS = "Female Juniors";
    public static final String FEMALE_OLDER_JUNIORS = "Female Older Juniors";
    public static final String FEMALE_SENIOR = "Female Seniors";


    private ArrayList<String> ageCategories = new ArrayList<>();

    public CategoryFactory() {
        ageCategories.add(MALE_YOUTH);
        ageCategories.add(MALE_OLDER_YOUTH);
        ageCategories.add(MALE_JUNIORS);
        ageCategories.add(MALE_OLDER_JUNIORS);
        ageCategories.add(MALE_SENIOR);
        ageCategories.add(MALE_VETERANS);
        ageCategories.add(FEMALE_YOUTH);
        ageCategories.add(FEMALE_OLDER_YOUTH);
        ageCategories.add(FEMALE_JUNIORS);
        ageCategories.add(FEMALE_OLDER_JUNIORS);
        ageCategories.add(FEMALE_SENIOR);
    }

    public ArrayList<String> getAgeCategoties() {
        return ageCategories;
    }

    public ArrayList<String> getWeightCategoryByAge(String ageCategory) {
        if (ageCategory == null || ageCategory.equals("")) {
            System.err.println("Category factory : getWeightCategoryByAge : ageCategory is null");
            return null;
        }
        switch (ageCategory) {
            case MALE_YOUTH:
                return getMaleYouthWeightCategory();
            case MALE_OLDER_YOUTH:
                return getMaleOlderYouthWeightCategory();
            case MALE_JUNIORS:
                return getMaleJuniorsWeightCategory();
            case MALE_OLDER_JUNIORS:
                return getMaleOlderJuniorsWeightCategory();
            case MALE_SENIOR:
                return getMaleSeniorsWeightCategory();
            case MALE_VETERANS:
                return getMaleVeteransWeightCategory();
            case FEMALE_YOUTH:
                return getFemaleYouthWeightCategory();
            case FEMALE_OLDER_YOUTH:
                return getFemaleOlderYouthWeightCategory();
            case FEMALE_JUNIORS:
                return getFemaleJuniorsWeightCategory();
            case FEMALE_OLDER_JUNIORS:
                return getFemaleOlderJuniorsWeightCategory();
            case FEMALE_SENIOR:
                return getFemaleSeniorsWeightCategory();

        }
        return null;
    }

    public ArrayList getFemaleSeniorsWeightCategory() {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("nothing");
        categories.add("-51kg");
        categories.add("-57kg");
        categories.add("-63kg");
        categories.add("-69kg");
        categories.add("-75kg");
        categories.add("+75kg");
        return categories;
    }

    private ArrayList getFemaleOlderJuniorsWeightCategory() {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("nothing");
        categories.add("-46kg");
        categories.add("-52kg");
        categories.add("-58kg");
        categories.add("-64kg");
        categories.add("-70kg");
        categories.add("+70kg");
        return categories;
    }

    private ArrayList getFemaleJuniorsWeightCategory() {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("nothing");
        categories.add("-46kg");
        categories.add("-52kg");
        categories.add("-58kg");
        categories.add("-64kg");
        categories.add("-70kg");
        categories.add("+70kg");
        return categories;
    }

    private ArrayList getFemaleOlderYouthWeightCategory() {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("nothing");
        categories.add("-30kg");
        categories.add("-35kg");
        categories.add("-40kg");
        categories.add("-45kg");
        categories.add("-50kg");
        categories.add("-55kg");
        categories.add("+55kg");
        return categories;
    }

    private ArrayList getFemaleYouthWeightCategory() {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("nothing");
        categories.add("-25kg");
        categories.add("-30kg");
        categories.add("-35kg");
        categories.add("-40kg");
        categories.add("-45kg");
        categories.add("+45kg");
        return categories;
    }

    private ArrayList getMaleVeteransWeightCategory() {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("nothing");
        categories.add("-80kg");
        categories.add("+80kg");
        return categories;
    }

    private ArrayList getMaleSeniorsWeightCategory() {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("nothing");
        categories.add("-57kg");
        categories.add("-64kg");
        categories.add("-71kg");
        categories.add("-78kg");
        categories.add("-85kg");
        categories.add("+85kg");
        return categories;
    }

    private ArrayList getMaleOlderJuniorsWeightCategory() {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("nothing");
        categories.add("-45kg");
        categories.add("-51kg");
        categories.add("-57kg");
        categories.add("-63kg");
        categories.add("-69kg");
        categories.add("-75kg");
        categories.add("+75kg");
        return categories;
    }

    private ArrayList getMaleJuniorsWeightCategory() {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("nothing");
        categories.add("-45kg");
        categories.add("-51kg");
        categories.add("-57kg");
        categories.add("-63kg");
        categories.add("-69kg");
        categories.add("-75kg");
        categories.add("+75kg");
        return categories;
    }

    private ArrayList getMaleOlderYouthWeightCategory() {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("nothing");
        categories.add("-35kg");
        categories.add("-40kg");
        categories.add("-45kg");
        categories.add("-50kg");
        categories.add("-55kg");
        categories.add("-60kg");
        categories.add("+60kg");
        return categories;
    }

    private ArrayList getMaleYouthWeightCategory() {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("nothing");
        categories.add("-25kg");
        categories.add("-30kg");
        categories.add("-35kg");
        categories.add("-40kg");
        categories.add("-45kg");
        categories.add("+45kg");
        return categories;
    }


    public ArrayList<String> getMaleCategoriesForAddDialog() {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("nothing");
        categories.add("Youth");
        categories.add("Older Youth");
        categories.add("Juniors");
        categories.add("Older Juniors");
        categories.add("Seniors");
        categories.add("Veterans");
        return categories;
    }

    public ArrayList<String> getFemaleCategoriesForAddDialog() {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("nothing");
        categories.add("Youth");
        categories.add("Older Youth");
        categories.add("Juniors");
        categories.add("Older Juniors");
        categories.add("Seniors");
        return categories;
    }
}
