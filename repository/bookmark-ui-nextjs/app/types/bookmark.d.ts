export interface BookmarksResponse{

    data: Bookmark[],
    totalElements: number,
    totalPages: number,
    currentPage: number,
    hasNext: boolean,
    hasPrevious: boolean,
    isFirst: boolean,
    isLast: boolean,
}

export interface Bookmark{
    id: number,
    title: string,
    url: string,
    createdAt: Date,
}