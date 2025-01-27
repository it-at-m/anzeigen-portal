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
 * @interface AdCategory
 */
export interface AdCategory {
    /**
     * 
     * @type {number}
     * @memberof AdCategory
     */
    id?: number;
    /**
     * 
     * @type {string}
     * @memberof AdCategory
     */
    name?: string;
    /**
     * 
     * @type {boolean}
     * @memberof AdCategory
     */
    standard?: boolean;
}

/**
 * Check if a given object implements the AdCategory interface.
 */
export function instanceOfAdCategory(value: object): value is AdCategory {
    return true;
}

export function AdCategoryFromJSON(json: any): AdCategory {
    return AdCategoryFromJSONTyped(json, false);
}

export function AdCategoryFromJSONTyped(json: any, ignoreDiscriminator: boolean): AdCategory {
    if (json == null) {
        return json;
    }
    return {
        
        'id': json['id'] == null ? undefined : json['id'],
        'name': json['name'] == null ? undefined : json['name'],
        'standard': json['standard'] == null ? undefined : json['standard'],
    };
}

export function AdCategoryToJSON(json: any): AdCategory {
    return AdCategoryToJSONTyped(json, false);
}

export function AdCategoryToJSONTyped(value?: AdCategory | null, ignoreDiscriminator: boolean = false): any {
    if (value == null) {
        return value;
    }

    return {
        
        'id': value['id'],
        'name': value['name'],
        'standard': value['standard'],
    };
}

