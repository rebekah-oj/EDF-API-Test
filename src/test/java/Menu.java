import io.restassured.RestAssured;
import io.restassured.internal.common.assertion.AssertionSupport;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Menu {

    String token = " eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIzOCIsImp0aSI6ImM1YmRlYTcxOGIwZjFhYTIxMTRlNTVlNmY4NGVlMmFjZTVkMDQ1NTlmYTVlZDhhNmJlZjI1MDA4MGQ1NzEzZTc3NGNlMGNlZmU4ZDE1Y2NhIiwiaWF0IjoiMTY3MDUwMzQ4NS40NjU0MTQiLCJuYmYiOiIxNjcwNTAzNDg1LjQ2NTQxOCIsImV4cCI6IjE3MDIwMzk0ODUuNDYxNjM5Iiwic3ViIjoiMjc2NzEiLCJzY29wZXMiOltdfQ.ljDKasZBAYyT6xITH4QGha7Ba9TmMXqFskWxwCh8U7krV8EAPVX8zfgRVAMwih6UyfuW6J2gsSzEAAG7cB2KDgqeK6I-B6WgbwqK_Z--lAiLnUoH8eUYh-J4E5uvZ1tQpm7dlT7xShOqQuQquXZigJdE8ucG1CUxq4LXv4oLsoysASp3n4VS9aFmgQGHRHhG88ip4k3myG8FYLAI7XRbD1hZvXWs_yNETDhxvlS2Ijpjw6PkgJxNDxUJ4HT2M8OfVeIDcYn-d6kUlSQOmu-3HjpTGXFTF7xmdEL27CFVIXA3aJ3U5eFtm6D0qfmC_aHOb0MweY_KDAgSoXr9pc7932EGVGHZUiFyAledxGqgLYirOQYH8HYFR5bd_f_mejdXMhHEAC6VQLNhhDG_-_H6zj-sGS050AGUHNDrsve_WWMO4cexYSoVpw78raHYO3CigxijefQz7zlLHffthV54-vTZ0_QZrZUMhbIWB2xLuPgSlOVC_AbLqSXkgXQXdB812E6uP3WKJjRBRHZ5pf1uiIS3vNI_irc7-0WyhfyzIWe_IrDcqcNuL_C2U77TT4uDbpd3e2vgITWQHNVKRM9cjeFvVE1kqK4Kf_vULgSrH0ig5GfkqkWoBd2Ak_-dFaGJEd4l6fhycKPbmz3N7YVA10k1-ZIcAqnwPCyrpS3KfDE";

    @Test
    public void createMenu(){

        String createMenuPayload = """
                {
                 "full_name": "Rice and Beans",
                 "quantity": 20,
                 "category_id": 1,
                 "image_url": "https://res.cloudinary.image"
                }""";

        RestAssured.baseURI = "https://api-onetime-orders.edenlife.ng";

        Response response = given()
                .header("Authorization", "Bearer" + token)
                .header("Content-Type", "application/json")
                .and()
                .body(createMenuPayload)
                .when()
                .post("/api/v2/festival/menu")
                .then()
                .extract().response();

        response.prettyPrint();

        String message = response.getBody().path("message");

        Assert.assertEquals(201, response.statusCode());
        Assert.assertEquals("Menu Item created successfully", message);
    }

    @Test
    public void getMenuItems(){

        RestAssured.baseURI = "https://api-onetime-orders.edenlife.ng";

        Response response = given()
                .header("Authorization", "Bearer" + token)
                .header("Content-Type", "application/json")
                .when()
                .get("/api/v2/festival/menu?filter=all")
                .then()
                .extract().response();

        response.prettyPrint();
        String message = response.getBody().path("message");
        JsonPath jsonPath = response.jsonPath();
        //int price = Integer.parseInt(jsonPath.getString("data[2].price"));

        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("Menu items fetched successfully", message);
       // Assert.assertEquals(3500, price);

    }

    @Test
    public void editMenuItems(){

        String payload = """
                {
                "full_name": "Rice and beans + stew",
                "quantity": 100,
                "category_id": 1,
                "image_url": "https://res.cloudinary.image"
                }
                """;

        RestAssured.baseURI = "https://api-onetime-orders.edenlife.ng";

        Response response = given()
                .header("Authorization", "Bearer" + token)
                .header("Content-Type", "application/json")
                .and()
                .body(payload)
                .when()
                .patch("/api/v2/festival/menu/1")
                .then().extract().response();

        response.prettyPrint();
        String message = response.getBody().path("message");

        JsonPath jsonPath = response.jsonPath();
        int price = Integer.parseInt(jsonPath.getString("data.price"));

        System.out.println(price);

        Assert.assertEquals("Menu item updated successfully", message);
        Assert.assertEquals(3500, price);
    }

    public void removeMenuItems(){

        RestAssured.baseURI = "https://api-onetime-orders.edenlife.ng";

        Response response = given()
                .header("Authorization", "Bearer" + token)
                .header("Content-Type", "application/json")
                .when().delete("");

    }

}
