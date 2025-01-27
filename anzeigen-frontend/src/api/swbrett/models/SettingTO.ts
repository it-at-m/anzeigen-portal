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
import type { SwbFileTO } from './SwbFileTO';
import {
    SwbFileTOFromJSON,
    SwbFileTOFromJSONTyped,
    SwbFileTOToJSON,
    SwbFileTOToJSONTyped,
} from './SwbFileTO';

/**
 * 
 * @export
 * @interface SettingTO
 */
export interface SettingTO {
    /**
     * 
     * @type {number}
     * @memberof SettingTO
     */
    id?: number;
    /**
     * 
     * @type {string}
     * @memberof SettingTO
     */
    settingName?: SettingTOSettingNameEnum;
    /**
     * 
     * @type {number}
     * @memberof SettingTO
     */
    numberValue?: number;
    /**
     * 
     * @type {string}
     * @memberof SettingTO
     */
    textValue?: string;
    /**
     * 
     * @type {boolean}
     * @memberof SettingTO
     */
    flagValue?: boolean;
    /**
     * 
     * @type {SwbFileTO}
     * @memberof SettingTO
     */
    fileValue?: SwbFileTO;
}


/**
 * @export
 */
export const SettingTOSettingNameEnum = {
    MOTD: 'MOTD',
    MAX_SWB_IMAGE_SIZE: 'MAX_SWB_IMAGE_SIZE',
    MAX_SWB_FILE_SIZE: 'MAX_SWB_FILE_SIZE',
    MAX_SWB_FILES_LENGTH: 'MAX_SWB_FILES_LENGTH',
    MAX_EXPIRY_DATE_RANGE: 'MAX_EXPIRY_DATE_RANGE',
    AGB_FILE: 'AGB_FILE',
    DATENSCHUTZHINWEISE_FILE: 'DATENSCHUTZHINWEISE_FILE',
    MAX_PAGE_SIZE: 'MAX_PAGE_SIZE',
    DEFAULT_SORTING: 'DEFAULT_SORTING',
    DEFAULT_ORDERING: 'DEFAULT_ORDERING',
    MAX_ARCHIVE_DATE_RANGE: 'MAX_ARCHIVE_DATE_RANGE'
} as const;
export type SettingTOSettingNameEnum = typeof SettingTOSettingNameEnum[keyof typeof SettingTOSettingNameEnum];


/**
 * Check if a given object implements the SettingTO interface.
 */
export function instanceOfSettingTO(value: object): value is SettingTO {
    return true;
}

export function SettingTOFromJSON(json: any): SettingTO {
    return SettingTOFromJSONTyped(json, false);
}

export function SettingTOFromJSONTyped(json: any, ignoreDiscriminator: boolean): SettingTO {
    if (json == null) {
        return json;
    }
    return {
        
        'id': json['id'] == null ? undefined : json['id'],
        'settingName': json['settingName'] == null ? undefined : json['settingName'],
        'numberValue': json['numberValue'] == null ? undefined : json['numberValue'],
        'textValue': json['textValue'] == null ? undefined : json['textValue'],
        'flagValue': json['flagValue'] == null ? undefined : json['flagValue'],
        'fileValue': json['fileValue'] == null ? undefined : SwbFileTOFromJSON(json['fileValue']),
    };
}

export function SettingTOToJSON(json: any): SettingTO {
    return SettingTOToJSONTyped(json, false);
}

export function SettingTOToJSONTyped(value?: SettingTO | null, ignoreDiscriminator: boolean = false): any {
    if (value == null) {
        return value;
    }

    return {
        
        'id': value['id'],
        'settingName': value['settingName'],
        'numberValue': value['numberValue'],
        'textValue': value['textValue'],
        'flagValue': value['flagValue'],
        'fileValue': SwbFileTOToJSON(value['fileValue']),
    };
}

