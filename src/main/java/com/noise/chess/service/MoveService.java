package com.noise.chess.service;

import com.noise.chess.core.Coordinates;
import com.noise.chess.core.Figure;
import com.noise.chess.core.FigureType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static com.noise.chess.core.Coordinates.X;
import static com.noise.chess.core.Coordinates.Y;
import static com.noise.chess.core.Coordinates.of;

@Service
public class MoveService {

    private FieldService fieldService;
    
    private final static Set<Direction> possibleDirections = new HashSet<>();

    @Autowired
    public MoveService(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    public Set<Coordinates> getPossibleMoves(Figure figure) {
        switch (figure.getFigureType()) {
            case King:
                return getKingMoves(figure);
            case Queen:
                return getQueenMoves(figure);
            case Bishop:
                return getBishopMoves(figure);
            case Knight:
                return getKnightMoves(figure);
            case Rook:
                return getRookMoves(figure);
            case Pawn:
                return getPawnMoves(figure);
            default:
                throw new RuntimeException("Unknown figure type " + figure.getFigureType());
        }
    }

    private Set<Coordinates> getPawnMoves(Figure figure) {
        Set<Coordinates> pawnMoves = new HashSet<>();

        possibleDirections.clear();

        int x = figure.getCoordinates().getX().ordinal();
        int y = figure.getCoordinates().getY().ordinal();

        //TODO: add diagonal attacking move

        switch (figure.getColor()) {
            case WHITE:
                possibleDirections.add(Direction.Down);
                add(pawnMoves, of(X.of(x), Y.of(y - 1)), figure, Direction.Down);
                add(pawnMoves, of(X.of(x), Y.of(y - 2)), figure, Direction.Down);
                break;
            case BLACK:
                possibleDirections.add(Direction.Up);
                add(pawnMoves, of(X.of(x), Y.of(y + 1)), figure, Direction.Up);
                add(pawnMoves, of(X.of(x), Y.of(y + 2)), figure, Direction.Up);
                break;
            default:
                throw new RuntimeException("Unknown color " + figure.getColor());
        }

        return pawnMoves;
    }

    private Set<Coordinates> getRookMoves(Figure figure) {
        Set<Coordinates> rookMoves = new HashSet<>();

        possibleDirections.clear();
        possibleDirections.add(Direction.Up);
        possibleDirections.add(Direction.Down);
        possibleDirections.add(Direction.Left);
        possibleDirections.add(Direction.Right);

        int x = figure.getCoordinates().getX().ordinal();
        int y = figure.getCoordinates().getY().ordinal();

        for (int i = 1; i < 8; i++) {
            add(rookMoves, of(X.of(x),     Y.of(y + i)), figure, Direction.Down);
            add(rookMoves, of(X.of(x + i), Y.of(y)),     figure, Direction.Right);
            add(rookMoves, of(X.of(x),     Y.of(y - i)), figure, Direction.Up);
            add(rookMoves, of(X.of(x - i), Y.of(y)),     figure, Direction.Left);
        }

        return rookMoves;
    }

    private Set<Coordinates> getKnightMoves(Figure figure) {
        Set<Coordinates> knightMoves = new HashSet<>();

        possibleDirections.clear();
        possibleDirections.add(Direction.Any);

        int x = figure.getCoordinates().getX().ordinal();
        int y = figure.getCoordinates().getY().ordinal();

        add(knightMoves, of(X.of(x - 2), Y.of(y - 1)), figure);
        add(knightMoves, of(X.of(x - 1), Y.of(y - 2)), figure);

        add(knightMoves, of(X.of(x + 2), Y.of(y - 1)), figure);
        add(knightMoves, of(X.of(x + 1), Y.of(y - 2)), figure);

        add(knightMoves, of(X.of(x - 2), Y.of(y + 1)), figure);
        add(knightMoves, of(X.of(x - 1), Y.of(y + 2)), figure);

        add(knightMoves, of(X.of(x + 2), Y.of(y + 1)), figure);
        add(knightMoves, of(X.of(x + 1), Y.of(y + 2)), figure);

        return knightMoves;
    }

    private Set<Coordinates> getBishopMoves(Figure figure) {
        Set<Coordinates> bishopMoves = new HashSet<>();

        possibleDirections.clear();
        possibleDirections.add(Direction.UpLeft);
        possibleDirections.add(Direction.UpRight);
        possibleDirections.add(Direction.DownLeft);
        possibleDirections.add(Direction.DownRight);

        int x = figure.getCoordinates().getX().ordinal();
        int y = figure.getCoordinates().getY().ordinal();

        for (int i = 1; i < 8; i++) {
            add(bishopMoves, of(X.of(x + i), Y.of(y + i)), figure, Direction.DownRight);
            add(bishopMoves, of(X.of(x + i), Y.of(y - i)), figure, Direction.UpRight);
            add(bishopMoves, of(X.of(x - i), Y.of(y - i)), figure, Direction.UpLeft);
            add(bishopMoves, of(X.of(x - i), Y.of(y + i)), figure, Direction.DownLeft);
        }
        
        return bishopMoves;
    }

    private Set<Coordinates> getQueenMoves(Figure figure) {
        Set<Coordinates> queenMoves = new HashSet<>();

        possibleDirections.clear();
        possibleDirections.add(Direction.Up);
        possibleDirections.add(Direction.Down);
        possibleDirections.add(Direction.Left);
        possibleDirections.add(Direction.Right);
        possibleDirections.add(Direction.UpLeft);
        possibleDirections.add(Direction.UpRight);
        possibleDirections.add(Direction.DownLeft);
        possibleDirections.add(Direction.DownRight);

        int x = figure.getCoordinates().getX().ordinal();
        int y = figure.getCoordinates().getY().ordinal();

        for (int i = 1; i < 8; i++) {
            add(queenMoves, of(X.of(x + i), Y.of(y + i)), figure, Direction.DownRight);
            add(queenMoves, of(X.of(x + i), Y.of(y)),     figure, Direction.Right);
            add(queenMoves, of(X.of(x + i), Y.of(y - i)), figure, Direction.UpRight);
            add(queenMoves, of(X.of(x),     Y.of(y + i)), figure, Direction.Down);
            add(queenMoves, of(X.of(x),     Y.of(y - i)), figure, Direction.Up);
            add(queenMoves, of(X.of(x - i), Y.of(y - i)), figure, Direction.UpLeft);
            add(queenMoves, of(X.of(x - i), Y.of(y)),     figure, Direction.Left);
            add(queenMoves, of(X.of(x - i), Y.of(y + i)), figure, Direction.DownLeft);
        }

        return queenMoves;
    }

    private Set<Coordinates> getKingMoves(Figure figure) {
        Set<Coordinates> kingMoves = new HashSet<>(8);

        possibleDirections.clear();
        possibleDirections.add(Direction.Up);
        possibleDirections.add(Direction.Down);
        possibleDirections.add(Direction.Left);
        possibleDirections.add(Direction.Right);
        possibleDirections.add(Direction.UpLeft);
        possibleDirections.add(Direction.UpRight);
        possibleDirections.add(Direction.DownLeft);
        possibleDirections.add(Direction.DownRight);

        int x = figure.getCoordinates().getX().ordinal();
        int y = figure.getCoordinates().getY().ordinal();

        add(kingMoves, of(X.of(x + 1), Y.of(y + 1)), figure, Direction.DownRight);
        add(kingMoves, of(X.of(x + 1), Y.of(y)),     figure, Direction.Right);
        add(kingMoves, of(X.of(x + 1), Y.of(y - 1)), figure, Direction.UpRight);
        add(kingMoves, of(X.of(x),     Y.of(y + 1)), figure, Direction.Down);
        add(kingMoves, of(X.of(x),     Y.of(y - 1)), figure, Direction.Up);
        add(kingMoves, of(X.of(x - 1), Y.of(y - 1)), figure, Direction.UpLeft);
        add(kingMoves, of(X.of(x - 1), Y.of(y)),     figure, Direction.Left);
        add(kingMoves, of(X.of(x - 1), Y.of(y + 1)), figure, Direction.DownLeft);

        return kingMoves;
    }

    private boolean add(Set<Coordinates> moves, Coordinates coordinates, Figure figure, Direction direction) {
        X x = coordinates.getX();
        Y y = coordinates.getY();

        if (x == null || y == null) {
            return possibleDirections.remove(direction);
        }

        boolean thereAreFiguresOnTheWay = fieldService.getField().stream()
            .anyMatch(f -> f.getCoordinates().getX() == x && f.getCoordinates().getY() == y);
        
        // Knights can jump
        if (figure.getFigureType() == FigureType.Knight && !thereAreFiguresOnTheWay) {
            return moves.add(coordinates);
        }

        if (thereAreFiguresOnTheWay) {
            return possibleDirections.remove(direction);
        }

        if (possibleDirections.contains(direction)) {
            return moves.add(coordinates);
        }

        return false;
    }

    private boolean add(Set<Coordinates> moves, Coordinates coordinates, Figure figure) {
        return add(moves, coordinates, figure, Direction.Any);
    }

    private enum Direction {
        Up, Down, Left, Right, UpRight, UpLeft, DownRight, DownLeft, Any
    }
}
