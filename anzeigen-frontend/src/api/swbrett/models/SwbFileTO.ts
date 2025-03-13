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

import { mapValues } from '../runtime';
/**
 * 
 * @export
 * @interface SwbFileTO
 */
export interface SwbFileTO {
    /**
     * 
     * @type {number}
     * @memberof SwbFileTO
     */
    id?: number;
    /**
     * 
     * @type {string}
     * @memberof SwbFileTO
     */
    name?: string;
    /**
     * 
     * @type {number}
     * @memberof SwbFileTO
     */
    size?: number;
    /**
     * 
     * @type {string}
     * @memberof SwbFileTO
     */
    fileBase64?: string;
}

/**
 * Check if a given object implements the SwbFileTO interface.
 */
export function instanceOfSwbFileTO(value: object): value is SwbFileTO {
    return true;
}

export function SwbFileTOFromJSON(json: any): SwbFileTO {
    return SwbFileTOFromJSONTyped(json, false);
}

export function SwbFileTOFromJSONTyped(json: any, ignoreDiscriminator: boolean): SwbFileTO {
    if (json == null) {
        return json;
    }
    return {
        
        'id': json['id'] == null ? undefined : json['id'],
        'name': json['name'] == null ? undefined : json['name'],
        'size': json['size'] == null ? undefined : json['size'],
        'fileBase64': json['fileBase64'] == null ? undefined : json['fileBase64'],
    };
}

  export function SwbFileTOToJSON(json: any): SwbFileTO {
      return SwbFileTOToJSONTyped(json, false);
  }

  export function SwbFileTOToJSONTyped(value?: SwbFileTO | null, ignoreDiscriminator: boolean = false): any {
    if (value == null) {
        return value;
    }

    return {
        
        'id': value['id'],
        'name': value['name'],
        'size': value['size'],
        'fileBase64': value['fileBase64'],
    };
}

