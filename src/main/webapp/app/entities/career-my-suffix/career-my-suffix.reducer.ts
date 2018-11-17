import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICareerMySuffix, defaultValue } from 'app/shared/model/career-my-suffix.model';

export const ACTION_TYPES = {
  FETCH_CAREER_LIST: 'career/FETCH_CAREER_LIST',
  FETCH_CAREER: 'career/FETCH_CAREER',
  CREATE_CAREER: 'career/CREATE_CAREER',
  UPDATE_CAREER: 'career/UPDATE_CAREER',
  DELETE_CAREER: 'career/DELETE_CAREER',
  SET_BLOB: 'career/SET_BLOB',
  RESET: 'career/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICareerMySuffix>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type CareerMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: CareerMySuffixState = initialState, action): CareerMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CAREER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CAREER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CAREER):
    case REQUEST(ACTION_TYPES.UPDATE_CAREER):
    case REQUEST(ACTION_TYPES.DELETE_CAREER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_CAREER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CAREER):
    case FAILURE(ACTION_TYPES.CREATE_CAREER):
    case FAILURE(ACTION_TYPES.UPDATE_CAREER):
    case FAILURE(ACTION_TYPES.DELETE_CAREER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_CAREER_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CAREER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CAREER):
    case SUCCESS(ACTION_TYPES.UPDATE_CAREER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CAREER):
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

const apiUrl = 'api/careers';

// Actions

export const getEntities: ICrudGetAllAction<ICareerMySuffix> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CAREER_LIST,
    payload: axios.get<ICareerMySuffix>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ICareerMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CAREER,
    payload: axios.get<ICareerMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ICareerMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CAREER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICareerMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CAREER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICareerMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CAREER,
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
