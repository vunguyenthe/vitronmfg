import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMainProductMySuffix, defaultValue } from 'app/shared/model/main-product-my-suffix.model';

export const ACTION_TYPES = {
  FETCH_MAINPRODUCT_LIST: 'mainProduct/FETCH_MAINPRODUCT_LIST',
  FETCH_MAINPRODUCT: 'mainProduct/FETCH_MAINPRODUCT',
  CREATE_MAINPRODUCT: 'mainProduct/CREATE_MAINPRODUCT',
  UPDATE_MAINPRODUCT: 'mainProduct/UPDATE_MAINPRODUCT',
  DELETE_MAINPRODUCT: 'mainProduct/DELETE_MAINPRODUCT',
  SET_BLOB: 'mainProduct/SET_BLOB',
  RESET: 'mainProduct/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMainProductMySuffix>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type MainProductMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: MainProductMySuffixState = initialState, action): MainProductMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MAINPRODUCT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MAINPRODUCT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MAINPRODUCT):
    case REQUEST(ACTION_TYPES.UPDATE_MAINPRODUCT):
    case REQUEST(ACTION_TYPES.DELETE_MAINPRODUCT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MAINPRODUCT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MAINPRODUCT):
    case FAILURE(ACTION_TYPES.CREATE_MAINPRODUCT):
    case FAILURE(ACTION_TYPES.UPDATE_MAINPRODUCT):
    case FAILURE(ACTION_TYPES.DELETE_MAINPRODUCT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MAINPRODUCT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_MAINPRODUCT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MAINPRODUCT):
    case SUCCESS(ACTION_TYPES.UPDATE_MAINPRODUCT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MAINPRODUCT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.SET_BLOB:
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/main-products';

// Actions

export const getEntities: ICrudGetAllAction<IMainProductMySuffix> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_MAINPRODUCT_LIST,
  payload: axios.get<IMainProductMySuffix>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IMainProductMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MAINPRODUCT,
    payload: axios.get<IMainProductMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMainProductMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MAINPRODUCT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMainProductMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MAINPRODUCT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMainProductMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MAINPRODUCT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
