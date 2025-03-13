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
import type { SwbImageTO } from './SwbImageTO';
import {
    SwbImageTOFromJSON,
    SwbImageTOFromJSONTyped,
    SwbImageTOToJSON,
    SwbImageTOToJSONTyped,
} from './SwbImageTO';
import type { SwbFileTO } from './SwbFileTO';
import {
    SwbFileTOFromJSON,
    SwbFileTOFromJSONTyped,
    SwbFileTOToJSON,
    SwbFileTOToJSONTyped,
} from './SwbFileTO';
import type { SwbUserTO } from './SwbUserTO';
import {
    SwbUserTOFromJSON,
    SwbUserTOFromJSONTyped,
    SwbUserTOToJSON,
    SwbUserTOToJSONTyped,
} from './SwbUserTO';
import type { AdCategory } from './AdCategory';
import {
    AdCategoryFromJSON,
    AdCategoryFromJSONTyped,
    AdCategoryToJSON,
    AdCategoryToJSONTyped,
} from './AdCategory';

/**
 * 
 * @export
 * @interface AdTO
 */
export interface AdTO {
    /**
     * 
     * @type {number}
     * @memberof AdTO
     */
    id?: number;
    /**
     * 
     * @type {SwbUserTO}
     * @memberof AdTO
     */
    swbUser?: SwbUserTO;
    /**
     * 
     * @type {AdCategory}
     * @memberof AdTO
     */
    adCategory?: AdCategory;
    /**
     * 
     * @type {string}
     * @memberof AdTO
     */
    adType?: AdTOAdTypeEnum;
    /**
     * 
     * @type {boolean}
     * @memberof AdTO
     */
    active?: boolean;
    /**
     * 
     * @type {string}
     * @memberof AdTO
     */
    title?: string;
    /**
     * 
     * @type {string}
     * @memberof AdTO
     */
    description?: string;
    /**
     * 
     * @type {number}
     * @memberof AdTO
     */
    price?: number;
    /**
     * 
     * @type {string}
     * @memberof AdTO
     */
    phone?: string;
    /**
     * 
     * @type {string}
     * @memberof AdTO
     */
    email?: string;
    /**
     * 
     * @type {string}
     * @memberof AdTO
     */
    link?: string;
    /**
     * 
     * @type {Date}
     * @memberof AdTO
     */
    creationDateTime?: Date;
    /**
     * 
     * @type {Date}
     * @memberof AdTO
     */
    expiryDate?: Date;
    /**
     * 
     * @type {string}
     * @memberof AdTO
     */
    imagePreviewBase64?: string;
    /**
     * 
     * @type {SwbImageTO}
     * @memberof AdTO
     */
    adImg?: SwbImageTO;
    /**
     * 
     * @type {Array<SwbFileTO>}
     * @memberof AdTO
     */
    adFiles?: Array<SwbFileTO>;
    /**
     * 
     * @type {number}
     * @memberof AdTO
     */
    views?: number;
}


/**
 * @export
 */
export const AdTOAdTypeEnum = {
    SEEK: 'SEEK',
    OFFER: 'OFFER'
} as const;
export type AdTOAdTypeEnum = typeof AdTOAdTypeEnum[keyof typeof AdTOAdTypeEnum];


/**
 * Check if a given object implements the AdTO interface.
 */
export function instanceOfAdTO(value: object): value is AdTO {
    return true;
}

export function AdTOFromJSON(json: any): AdTO {
    return AdTOFromJSONTyped(json, false);
}

export function AdTOFromJSONTyped(json: any, ignoreDiscriminator: boolean): AdTO {
    if (json == null) {
        return json;
    }
    return {
        
        'id': json['id'] == null ? undefined : json['id'],
        'swbUser': json['swbUser'] == null ? undefined : SwbUserTOFromJSON(json['swbUser']),
        'adCategory': json['adCategory'] == null ? undefined : AdCategoryFromJSON(json['adCategory']),
        'adType': json['adType'] == null ? undefined : json['adType'],
        'active': json['active'] == null ? undefined : json['active'],
        'title': json['title'] == null ? undefined : json['title'],
        'description': json['description'] == null ? undefined : json['description'],
        'price': json['price'] == null ? undefined : json['price'],
        'phone': json['phone'] == null ? undefined : json['phone'],
        'email': json['email'] == null ? undefined : json['email'],
        'link': json['link'] == null ? undefined : json['link'],
        'creationDateTime': json['creationDateTime'] == null ? undefined : (new Date(json['creationDateTime'])),
        'expiryDate': json['expiryDate'] == null ? undefined : (new Date(json['expiryDate'])),
        'imagePreviewBase64': json['imagePreviewBase64'] == null ? undefined : json['imagePreviewBase64'],
        'adImg': json['adImg'] == null ? undefined : SwbImageTOFromJSON(json['adImg']),
        'adFiles': json['adFiles'] == null ? undefined : ((json['adFiles'] as Array<any>).map(SwbFileTOFromJSON)),
        'views': json['views'] == null ? undefined : json['views'],
    };
}

  export function AdTOToJSON(json: any): AdTO {
      return AdTOToJSONTyped(json, false);
  }

  export function AdTOToJSONTyped(value?: AdTO | null, ignoreDiscriminator: boolean = false): any {
    if (value == null) {
        return value;
    }

    return {
        
        'id': value['id'],
        'swbUser': SwbUserTOToJSON(value['swbUser']),
        'adCategory': AdCategoryToJSON(value['adCategory']),
        'adType': value['adType'],
        'active': value['active'],
        'title': value['title'],
        'description': value['description'],
        'price': value['price'],
        'phone': value['phone'],
        'email': value['email'],
        'link': value['link'],
        'creationDateTime': value['creationDateTime'] == null ? undefined : ((value['creationDateTime']).toISOString()),
        'expiryDate': value['expiryDate'] == null ? undefined : ((value['expiryDate']).toISOString().substring(0,10)),
        'imagePreviewBase64': value['imagePreviewBase64'],
        'adImg': SwbImageTOToJSON(value['adImg']),
        'adFiles': value['adFiles'] == null ? undefined : ((value['adFiles'] as Array<any>).map(SwbFileTOToJSON)),
        'views': value['views'],
    };
}

