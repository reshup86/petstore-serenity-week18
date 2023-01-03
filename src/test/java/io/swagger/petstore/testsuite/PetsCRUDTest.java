package io.swagger.petstore.testsuite;

import io.restassured.response.ValidatableResponse;
import io.swagger.petstore.info.PetsSteps;
import io.swagger.petstore.testbase.TestBase;
import io.swagger.petstore.utils.TestUtils;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class PetsCRUDTest extends TestBase {
    static long petId;

    static HashMap<String, Object> category;

    static String name = "Suzzy" + TestUtils.getRandomValue();

    static ArrayList<String> photoUrls;

    static ArrayList<HashMap<String, Object>> tags;

    static String status = "available";


    @Steps
    PetsSteps petsSteps;

    @Title("This will create new Pet")
    @Test
    public void test001() {

        HashMap<String, Object> category = new HashMap<>();
        category.put("id", "2");
        category.put("name", "Mixed");

        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add("String");

        ArrayList<HashMap<String, Object>> tags = new ArrayList<>();
        HashMap<String, Object> tagMap = new HashMap<>();
        tagMap.put("id", 301);
        tagMap.put("name", "Suzzy");
        tags.add(tagMap);

        ValidatableResponse response = petsSteps.createPet(petId, category, name, photoUrls, tags, status);
        response.log().all().statusCode(200);
        petId = response.log().all().extract().path("id");
    }

    @Title("This will verify if the pet was created")
    @Test
    public void test002() {
        HashMap<String, Object> petMap = petsSteps.findPetById(petId);
        Assert.assertThat(petMap, hasValue(name));
    }

    @Title("Update the pet details and verify")
    @Test
    public void test003() {
        status = status + "_updated";

        HashMap<String, Object> category = new HashMap<>();
        category.put("name", "Mixed");
        category.put("id", "2");

        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add("https://th.bing.com/th/id/OIP.L90UXlIDXPcyWOOnOIjlggHaFa?pid=ImgDet&rs=1");

        ArrayList<HashMap<String, Object>> tags = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> tagMap = new HashMap<>();
        tagMap.put("id", 301);
        tagMap.put("name", "Suzzy");
        tags.add(tagMap);

        petsSteps.updatePet(petId, category, name, photoUrls, tags, status);

        HashMap<String, Object> petMap = petsSteps.findPetById(petId);
        Assert.assertThat(petMap, anything(status));
    }

    @Title("Delete the pet by Id")
    @Test
    public void test004() {
        petsSteps.deletePetById(petId);
        petsSteps.getPetById(petId);
    }
}
