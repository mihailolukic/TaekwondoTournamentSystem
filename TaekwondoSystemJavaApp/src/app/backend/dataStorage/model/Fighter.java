package app.backend.dataStorage.model;

/**
 * Created by Mihailo on 1/29/2017.
 */
public class Fighter {
    private int id;
    private String timeMark;
    private String nameAndSurname;
    private String country;
    private String club;
    private String choose;
    private String gender;
    private String maleCategory;
    private String femaleCategory;
    private String degree;
    private String sparingYouth;
    private String sparingOlderYouthMale;
    private String sparingOlderYouthFemale;
    private String sparingJuniorsMale;
    private String sparingJuniorsFemale;
    private String sparingSeniorsMale;
    private String sparingSeniorsFemale;
    private String degreeUmpires;

    private String sparingVeterans;
    private String email;
    private String phone;
    private String dateOfBirth;

    public Fighter() {

    }

    public String getTimeMark() {
        return timeMark;
    }

    public void setTimeMark(String timeMark) {
        this.timeMark = timeMark;
    }

    public String getNameAndSurname() {
        return nameAndSurname;
    }

    public void setNameAndSurname(String nameAndSurname) {
        this.nameAndSurname = nameAndSurname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getChoose() {
        return choose;
    }

    public void setChoose(String choose) {
        this.choose = choose;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaleCategory() {
        return maleCategory;
    }

    public void setMaleCategory(String maleCategory) {
        this.maleCategory = maleCategory;
    }

    public String getFemaleCategory() {
        return femaleCategory;
    }

    public void setFemaleCategory(String femaleCategory) {
        this.femaleCategory = femaleCategory;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getSparingYouth() {
        return sparingYouth;
    }

    public void setSparingYouth(String sparingYouth) {
        this.sparingYouth = sparingYouth;
    }

    public String getSparingOlderYouthMale() {
        return sparingOlderYouthMale;
    }

    public void setSparingOlderYouthMale(String sparingOlderYouthMale) {
        this.sparingOlderYouthMale = sparingOlderYouthMale;
    }

    public String getSparingOlderYouthFemale() {
        return sparingOlderYouthFemale;
    }

    public void setSparingOlderYouthFemale(String sparingOlderYouthFemale) {
        this.sparingOlderYouthFemale = sparingOlderYouthFemale;
    }

    public String getSparingJuniorsMale() {
        return sparingJuniorsMale;
    }

    public void setSparingJuniorsMale(String sparingJuniorsMale) {
        this.sparingJuniorsMale = sparingJuniorsMale;
    }

    public String getSparingJuniorsFemale() {
        return sparingJuniorsFemale;
    }

    public void setSparingJuniorsFemale(String sparingJuniorsFemale) {
        this.sparingJuniorsFemale = sparingJuniorsFemale;
    }

    public String getSparingSeniorsMale() {
        return sparingSeniorsMale;
    }

    public void setSparingSeniorsMale(String sparingSeniorsMale) {
        this.sparingSeniorsMale = sparingSeniorsMale;
    }

    public String getSparingSeniorsFemale() {
        return sparingSeniorsFemale;
    }

    public void setSparingSeniorsFemale(String sparingSeniorsFemale) {
        this.sparingSeniorsFemale = sparingSeniorsFemale;
    }

    public String getDegreeUmpires() {
        return degreeUmpires;
    }

    public void setDegreeUmpires(String degreeUmpires) {
        this.degreeUmpires = degreeUmpires;
    }

    public String getSparingVeterans() {
        return sparingVeterans;
    }

    public void setSparingVeterans(String sparingVeterans) {
        this.sparingVeterans = sparingVeterans;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAgeCategory(){
        String result = getGender() + " ";
        if (maleCategory!=null && !maleCategory.isEmpty()){
            result += getMaleCategory();
        }
        else{
            result += getFemaleCategory();
        }
        return result;
    }

    public String getWeightCategory(){
        if(sparingYouth!=null && !sparingYouth.isEmpty()){
            return sparingYouth;
        }
        else if(sparingOlderYouthFemale!=null && !sparingOlderYouthFemale.isEmpty()){
            return sparingOlderYouthFemale;
        }
        else if(sparingOlderYouthMale!=null && !sparingOlderYouthMale.isEmpty()){
            return sparingOlderYouthMale;
        }
        else if(sparingJuniorsFemale!=null && !sparingJuniorsFemale.isEmpty()){
            return sparingJuniorsFemale;
        }
        else if(sparingJuniorsMale!=null && !sparingJuniorsMale.isEmpty()){
            return sparingJuniorsMale;
        }
        else if(sparingSeniorsFemale!=null && !sparingSeniorsFemale.isEmpty()){
            return sparingSeniorsFemale;
        }
        else if(sparingSeniorsMale!=null && !sparingSeniorsMale.isEmpty()){
            return sparingSeniorsMale;
        }
        else if(sparingVeterans!=null && !sparingVeterans.isEmpty()){
            return sparingVeterans;
        }
        else{
            return "";
        }

    }
}
