export interface INewsMySuffix {
  id?: number;
  title?: string;
  description?: string;
  content?: any;
  photoContentType?: string;
  photo?: any;
}

export const defaultValue: Readonly<INewsMySuffix> = {};
