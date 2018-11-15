import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { INewsMySuffix, defaultValue } from 'app/shared/model/news-my-suffix.model';

export const ACTION_TYPES = {
  FETCH_NEWS_LIST: 'news/FETCH_NEWS_LIST',
  FETCH_NEWS: 'news/FETCH_NEWS',
  CREATE_NEWS: 'news/CREATE_NEWS',
  UPDATE_NEWS: 'news/UPDATE_NEWS',
  DELETE_NEWS: 'news/DELETE_NEWS',
  SET_BLOB: 'news/SET_BLOB',
  RESET: 'news/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<INewsMySuffix>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type NewsMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: NewsMySuffixState = initialState, action): NewsMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_NEWS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_NEWS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_NEWS):
    case REQUEST(ACTION_TYPES.UPDATE_NEWS):
    case REQUEST(ACTION_TYPES.DELETE_NEWS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_NEWS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_NEWS):
    case FAILURE(ACTION_TYPES.CREATE_NEWS):
    case FAILURE(ACTION_TYPES.UPDATE_NEWS):
    case FAILURE(ACTION_TYPES.DELETE_NEWS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_NEWS_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_NEWS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_NEWS):
    case SUCCESS(ACTION_TYPES.UPDATE_NEWS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_NEWS):
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

const apiUrl = 'api/news';

// Actions

export const getEntities: ICrudGetAllAction<INewsMySuffix> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_NEWS_LIST,
    payload: axios.get<INewsMySuffix>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<INewsMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_NEWS,
    payload: axios.get<INewsMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<INewsMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_NEWS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<INewsMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_NEWS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<INewsMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_NEWS,
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
