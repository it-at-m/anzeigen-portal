/* tslint:disable */
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
 * Sort information
 * @export
 * @interface GetAds200ResponseSort
 */
export interface GetAds200ResponseSort {
    /**
     * 
     * @type {boolean}
     * @memberof GetAds200ResponseSort
     */
    sorted?: boolean;
    /**
     * 
     * @type {boolean}
     * @memberof GetAds200ResponseSort
     */
    unsorted?: boolean;
    /**
     * 
     * @type {boolean}
     * @memberof GetAds200ResponseSort
     */
    empty?: boolean;
}

/**
 * Check if a given object implements the GetAds200ResponseSort interface.
 */
export function instanceOfGetAds200ResponseSort(value: object): value is GetAds200ResponseSort {
    return true;
}

export function GetAds200ResponseSortFromJSON(json: any): GetAds200ResponseSort {
    return GetAds200ResponseSortFromJSONTyped(json, false);
}

export function GetAds200ResponseSortFromJSONTyped(json: any, ignoreDiscriminator: boolean): GetAds200ResponseSort {
    if (json == null) {
        return json;
    }
    return {
        
        'sorted': json['sorted'] == null ? undefined : json['sorted'],
        'unsorted': json['unsorted'] == null ? undefined : json['unsorted'],
        'empty': json['empty'] == null ? undefined : json['empty'],
    };
}

  export function GetAds200ResponseSortToJSON(json: any): GetAds200ResponseSort {
      return GetAds200ResponseSortToJSONTyped(json, false);
  }

  export function GetAds200ResponseSortToJSONTyped(value?: GetAds200ResponseSort | null, ignoreDiscriminator: boolean = false): any {
    if (value == null) {
        return value;
    }

    return {
        
        'sorted': value['sorted'],
        'unsorted': value['unsorted'],
        'empty': value['empty'],
    };
}

