/* eslint-disable */
/**
 * anzeigen_portal API
 * anzeigen_portal API
 *
 * The version of the OpenAPI document: 1.0.0
 *
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import { mapValues } from "../runtime";

/**
 *
 * @export
 * @interface SwbImageTO
 */
export interface SwbImageTO {
  /**
   *
   * @type {number}
   * @memberof SwbImageTO
   */
  id?: number;
  /**
   *
   * @type {string}
   * @memberof SwbImageTO
   */
  imageBase64?: string;
}

/**
 * Check if a given object implements the SwbImageTO interface.
 */
export function instanceOfSwbImageTO(value: object): value is SwbImageTO {
  return true;
}

export function SwbImageTOFromJSON(json: any): SwbImageTO {
  return SwbImageTOFromJSONTyped(json, false);
}

export function SwbImageTOFromJSONTyped(
  json: any,
  ignoreDiscriminator: boolean
): SwbImageTO {
  if (json == null) {
    return json;
  }
  return {
    id: json["id"] == null ? undefined : json["id"],
    imageBase64: json["imageBase64"] == null ? undefined : json["imageBase64"],
  };
}

export function SwbImageTOToJSON(json: any): SwbImageTO {
  return SwbImageTOToJSONTyped(json, false);
}

export function SwbImageTOToJSONTyped(
  value?: SwbImageTO | null,
  ignoreDiscriminator: boolean = false
): any {
  if (value == null) {
    return value;
  }

  return {
    id: value["id"],
    imageBase64: value["imageBase64"],
  };
}