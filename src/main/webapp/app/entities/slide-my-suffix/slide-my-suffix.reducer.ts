import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISlideMySuffix, defaultValue } from 'app/shared/model/slide-my-suffix.model';

export const ACTION_TYPES = {
  FETCH_SLIDE_LIST: 'slide/FETCH_SLIDE_LIST',
  FETCH_SLIDE: 'slide/FETCH_SLIDE',
  CREATE_SLIDE: 'slide/CREATE_SLIDE',
  UPDATE_SLIDE: 'slide/UPDATE_SLIDE',
  DELETE_SLIDE: 'slide/DELETE_SLIDE',
  SET_BLOB: 'slide/SET_BLOB',
  RESET: 'slide/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISlideMySuffix>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type SlideMySuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: SlideMySuffixState = initialState, action): SlideMySuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SLIDE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SLIDE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SLIDE):
    case REQUEST(ACTION_TYPES.UPDATE_SLIDE):
    case REQUEST(ACTION_TYPES.DELETE_SLIDE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_SLIDE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SLIDE):
    case FAILURE(ACTION_TYPES.CREATE_SLIDE):
    case FAILURE(ACTION_TYPES.UPDATE_SLIDE):
    case FAILURE(ACTION_TYPES.DELETE_SLIDE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_SLIDE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_SLIDE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SLIDE):
    case SUCCESS(ACTION_TYPES.UPDATE_SLIDE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SLIDE):
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

const apiUrl = 'api/slides';

// Actions

export const getEntities: ICrudGetAllAction<ISlideMySuffix> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_SLIDE_LIST,
  payload: axios.get<ISlideMySuffix>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<ISlideMySuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SLIDE,
    payload: axios.get<ISlideMySuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ISlideMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SLIDE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISlideMySuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SLIDE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISlideMySuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SLIDE,
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
