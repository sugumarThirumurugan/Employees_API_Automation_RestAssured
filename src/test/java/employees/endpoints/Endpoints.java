package employees.endpoints;
import java.util.ResourceBundle;

import employees.payloads.Payloads;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class Endpoints {

	public static ResourceBundle getUrl() {
		ResourceBundle routes = ResourceBundle.getBundle("Routes");
		return routes;
	}

	public static Response createEmployee(Payloads payloads) {
		String createUrl = getUrl().getString("createEmployee");
		Response createEmployee = given().accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.body(payloads)
				.when().post(createUrl);
		return createEmployee;
	}

	public static Response getEmployee(String id) {
		String getUrl = getUrl().getString("getEmployee");
		Response getEmployee = given().accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.pathParam("id", id)
				.when().get(getUrl);
		return getEmployee;
	}

	public static Response updateEmployee(String id,Payloads payloads) {
		String getUrl = getUrl().getString("updateEmployee");
		Response updateEmployee = given().accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.pathParam("id", id)
				.body(payloads)
				.when().put(getUrl);
		return updateEmployee;
	}

	public static Response deleteEmployee(String id) {
		String deleteUrl = getUrl().getString("deleteEmployee");
		Response deleteEmployee = given().accept(ContentType.JSON)
				.contentType(ContentType.JSON)
				.pathParam("id", id)
				.when().delete(deleteUrl);
		return deleteEmployee;
	}

}
