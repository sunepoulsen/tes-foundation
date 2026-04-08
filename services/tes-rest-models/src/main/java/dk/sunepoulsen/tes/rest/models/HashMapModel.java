package dk.sunepoulsen.tes.rest.models;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.HashMap;

/**
 * Model for dynamic json.
 * <p>
 *     It can represent any type of json.
 * </p>
 */
@Schema(name = "HashMapModel", description = "Model of a dynamic json")
public class HashMapModel extends HashMap<String, Object> {
}
