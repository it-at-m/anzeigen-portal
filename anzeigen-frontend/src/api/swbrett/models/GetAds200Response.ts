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
import type { AdTO } from './AdTO';
import {
    AdTOFromJSON,
    AdTOFromJSONTyped,
    AdTOToJSON,
    AdTOToJSONTyped,
} from './AdTO';
import type { GetAds200ResponseSort } from './GetAds200ResponseSort';
import {
    GetAds200ResponseSortFromJSON,
    GetAds200ResponseSortFromJSONTyped,
    GetAds200ResponseSortToJSON,
    GetAds200ResponseSortToJSONTyped,
} from './GetAds200ResponseSort';

/**
 * 
 * @export
 * @interface GetAds200Response
 */
export interface GetAds200Response {
    /**
     * 
     * @type {Array<AdTO>}
     * @memberof GetAds200Response
     */
    content?: Array<AdTO>;
    /**
     * 
     * @type {number}
     * @memberof GetAds200Response
     */
    totalElements?: number;
    /**
     * 
     * @type {number}
     * @memberof GetAds200Response
     */
    totalPages?: number;
    /**
     * 
     * @type {number}
     * @memberof GetAds200Response
     */
    size?: number;
    /**
     * 
     * @type {number}
     * @memberof GetAds200Response
     */
    number?: number;
    /**
     * 
     * @type {number}
     * @memberof GetAds200Response
     */
    numberOfElements?: number;
    /**
     * 
     * @type {GetAds200ResponseSort}
     * @memberof GetAds200Response
     */
    sort?: GetAds200ResponseSort;
    /**
     * 
     * @type {boolean}
     * @memberof GetAds200Response
     */
    first?: boolean;
    /**
     * 
     * @type {boolean}
     * @memberof GetAds200Response
     */
    last?: boolean;
}

/**
 * Check if a given object implements the GetAds200Response interface.
 */
export function instanceOfGetAds200Response(value: object): value is GetAds200Response {
    return true;
}

export function GetAds200ResponseFromJSON(json: any): GetAds200Response {
    return GetAds200ResponseFromJSONTyped(json, false);
}

export function GetAds200ResponseFromJSONTyped(json: any, ignoreDiscriminator: boolean): GetAds200Response {
    if (json == null) {
        return json;
    }
    return {
        
        'content': json['content'] == null ? undefined : ((json['content'] as Array<any>).map(AdTOFromJSON)),
        'totalElements': json['totalElements'] == null ? undefined : json['totalElements'],
        'totalPages': json['totalPages'] == null ? undefined : json['totalPages'],
        'size': json['size'] == null ? undefined : json['size'],
        'number': json['number'] == null ? undefined : json['number'],
        'numberOfElements': json['numberOfElements'] == null ? undefined : json['numberOfElements'],
        'sort': json['sort'] == null ? undefined : GetAds200ResponseSortFromJSON(json['sort']),
        'first': json['first'] == null ? undefined : json['first'],
        'last': json['last'] == null ? undefined : json['last'],
    };
}

  export function GetAds200ResponseToJSON(json: any): GetAds200Response {
      return GetAds200ResponseToJSONTyped(json, false);
  }

  export function GetAds200ResponseToJSONTyped(value?: GetAds200Response | null, ignoreDiscriminator: boolean = false): any {
    if (value == null) {
        return value;
    }

    return {
        
        'content': value['content'] == null ? undefined : ((value['content'] as Array<any>).map(AdTOToJSON)),
        'totalElements': value['totalElements'],
        'totalPages': value['totalPages'],
        'size': value['size'],
        'number': value['number'],
        'numberOfElements': value['numberOfElements'],
        'sort': GetAds200ResponseSortToJSON(value['sort']),
        'first': value['first'],
        'last': value['last'],
    };
}

