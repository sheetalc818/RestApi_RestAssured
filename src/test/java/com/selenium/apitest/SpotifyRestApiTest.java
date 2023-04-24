package com.selenium.apitest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;
public class SpotifyRestApiTest {

    String token;
    String userid;
    String playlistid;
    String username;
    String trackid = "6nDJIzi6BezsTcCdw8fmMg";

    @BeforeTest
    public void getToken() {
        //To generate the token follow the link https://developer.spotify.com/documentation/web-api/tutorials/implicit-flow
        //Then Create GET Request using endpoint like https://accounts.spotify.com/authorize?client_id={Enter_your_Client_ID}&response_type=token&redirect_uri=http://localhost:3000&scope=user-read-private playlist-read-private playlist-read-collaborative playlist-modify-private playlist-modify-public
        //Then Execute request in postman and then copy same endpoint and paste on Browser, in url will get the token for spotify
        token = "Bearer BQByKtp3w5iOkEJw_2ZWzY4TNc-CLAbjg1rnVupQY4qZwjvp2K2r5JPXQ7_qDskGdMvvwidTAi64FT4tTvaXKRdQq658TOS--hBKKAPAVRCsA7orXMV9hnaND_owMzRtxjRjHNdRxxxvWYUxqr-RWmo3RZjsHHufxhtJaMq4SdqCwz8RUjFINtB_okiMRThrxI2qMsbLw3BPOgO9NulshwDbbFj3f3krbXGaEuPiNTR5YcCGwElxQ0O84N3jj6lM2K-my0g_4PIBPfFGG3KO-w";
    }


    @Test(priority = 2)

    public void get_Current_User_Profile() {

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/me");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        userid = response.path("id");
        username = response.path("display_name");
        System.out.println("My current user id is = "+ userid);
        System.out.println("My current username is = "+ username);
        response.then().log().all();
        System.out.println("Response Time of get_Current_User_Profile in ms = "+ response.timeIn(TimeUnit.MILLISECONDS) );
    }

    @Test(priority = 1)

    public void get_User_Profile() {

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/users/"+userid+"");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        System.out.println("Response Time of get_User_Profile in ms = "+ response.timeIn(TimeUnit.MICROSECONDS));
    }

    @Test(priority = 3)

    public void Create_Playlist() {

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization",token)
                .body("{\n"
                        + "  \"name\": \"Udit Narayan Hits\",\n"
                        + "  \"description\": \"New playlist description\",\n"
                        + "  \"public\": false\n"
                        + "}")
                .when()
                .post("https://api.spotify.com/v1/users/"+userid+"/playlists");
        response.prettyPrint();
        response.then().assertThat().statusCode(201);
        playlistid = response.path("id");
        System.out.println("My current playlist id is = "+ playlistid);
        System.out.println("Response Time of Create_Playlist in ms = "+ response.timeIn(TimeUnit.MICROSECONDS));

    }

    @Test(priority = 4)

    public void Search_for_item() {

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization",token)
                .queryParam("q", "udit narayan")
                .queryParam("type","track")
                .when()
                .get("https://api.spotify.com/v1/search");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        System.out.println("Response Time of Search_for_item in ms = "+ response.timeIn(TimeUnit.MICROSECONDS));

    }

    @Test(priority = 5)


    public void Add_items_to_Playlist () {

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization",token)
                .queryParam("uris","spotify:track:6nDJIzi6BezsTcCdw8fmMg,spotify:track:4RixtPhUXlci9ad4qIMj23,spotify:track:1QwVVMQ6AglyGK6CgRzkbz")

                .when()
                .post("https://api.spotify.com/v1/playlists/"+playlistid+"/tracks");
        response.prettyPrint();
        response.then().assertThat().statusCode(201);
        System.out.println("Response Time of Add_items_to_Playlist in ms = "+ response.timeIn(TimeUnit.MICROSECONDS));

    }

    @Test(priority = 6)

    public void Get_Playlist_Items() {

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/playlists/"+playlistid+"/tracks");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        System.out.println("Response Time of Get_Playlist_Items in ms = "+ response.timeIn(TimeUnit.MICROSECONDS));



    }

    @Test(priority = 17)
    public void Remove_Playlist_Items () {

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization",token)
                .body("{\n"
                        + "    \"tracks\": [\n"
                        + "        {\n"
                        + "            \"uri\": \"spotify:track:6nDJIzi6BezsTcCdw8fmMg\",\n"
                        + "            \"positions\": [0]\n"
                        + "        }\n"
                        + "        \n"
                        + "    ]\n"
                        + "}")
                .when()
                .delete("https://api.spotify.com/v1/playlists/"+playlistid+"/tracks");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        System.out.println("Response Time of Remove_Playlist_Items in ms = "+ response.timeIn(TimeUnit.MICROSECONDS));

    }

    @Test(priority = 8)

    public void Get_Playlist () {

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/playlists/"+playlistid+"");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        System.out.println("Response Time of Get_Playlist in ms = "+ response.timeIn(TimeUnit.MICROSECONDS));

    }

    @Test(priority = 9)

    public void Get_Users_Playlists () {

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/users/"+userid+"/playlists");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        System.out.println("Response Time of Get_Users_Playlists in ms = "+ response.timeIn(TimeUnit.MICROSECONDS));

    }

    @Test(priority = 10)

    public void Get_Current_Users_Playlists () {

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/me/playlists");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        System.out.println("Response Time of Get_Current_Users_Playlists in ms = "+ response.timeIn(TimeUnit.MICROSECONDS));

    }

    @Test(priority = 11)

    public void Get_Playlist_Cover_Image() {

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/playlists/"+playlistid+"/images");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        System.out.println("Response Time of Get_Playlist_Cover_Image in ms = "+ response.timeIn(TimeUnit.MICROSECONDS));

    }

    @Test(priority = 12)

    public void Update_Playlist_Items() {

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization",token)
                .body("{\n"
                        + "  \"range_start\": 1,\n"
                        + "  \"insert_before\": 3,\n"
                        + "  \"range_length\": 2\n"
                        + "}")

                .when()
                .put("https://api.spotify.com/v1/playlists/"+playlistid+"/tracks");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        System.out.println("Response Time of Update_Playlist_Items in ms = "+ response.timeIn(TimeUnit.MICROSECONDS));

    }

    @Test(priority = 13)

    public void Change_Playlist_Details() {

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization",token)
                .body("{\n"
                        + "  \"name\": \"udit ji\",\n"
                        + "  \"description\": \"Updated playlist description\",\n"
                        + "  \"public\": false\n"
                        + "}")

                .when()
                .put("	https://api.spotify.com/v1/playlists/"+playlistid+"");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        System.out.println("Response Time of Change_Playlist_Details in ms = "+ response.timeIn(TimeUnit.MICROSECONDS));


    }

    @Test(priority = 14)


    public void Get_Tracks() {

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/tracks/"+trackid+"");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        System.out.println("Response Time of Get_Tracks in ms = "+ response.timeIn(TimeUnit.MICROSECONDS));

    }

    @Test(priority = 15)

    public void Get_Tracks_Audio_Analysis() {

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization",token)
                .when()
                .get("	https://api.spotify.com/v1/audio-analysis/"+trackid+"");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        System.out.println("Response Time of Get_Tracks_Audio_Analysis in ms = "+ response.timeIn(TimeUnit.MICROSECONDS));
    }

    @Test(priority = 16)

    public void Get_Tracks_Audio_Features() {

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization",token)
                .queryParam("ids","4XEtEmnRgmNtDH9SayLCRR" )
                .when()
                .get("https://api.spotify.com/v1/audio-features");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        System.out.println("Response Time of Get_Tracks_Audio_Features in ms = "+ response.timeIn(TimeUnit.MICROSECONDS));
    }

    @Test(priority = 17)

    public void Get_Track_Audio_Features() {

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/audio-features/"+trackid+"");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        System.out.println("Response Time of Get_Track_Audio_Features in ms = "+ response.timeIn(TimeUnit.MICROSECONDS));
    }

    @Test(priority = 7)

    public void Get_Several_Tracks() {

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization",token)
                .queryParam("ids","4XEtEmnRgmNtDH9SayLCRR" )
                .when()
                .get("	https://api.spotify.com/v1/tracks");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
        System.out.println("Response Time of Get_Several_Tracks in ms = "+ response.timeIn(TimeUnit.MICROSECONDS));

    }
}
